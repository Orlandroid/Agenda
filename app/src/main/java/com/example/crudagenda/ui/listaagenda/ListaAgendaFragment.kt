package com.example.crudagenda.ui.listaagenda

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentListaAgendaBinding
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.ui.MainActivity
import com.example.crudagenda.ui.base.BaseFragment
import com.example.crudagenda.ui.update.ViewModelUpdate
import com.example.crudagenda.util.AlertMessageDialog
import com.example.crudagenda.util.ListenerAlertDialog
import com.example.crudagenda.util.ResultData
import com.example.crudagenda.util.gone
import com.example.crudagenda.util.showErrorApi
import com.example.crudagenda.util.showProgress
import com.example.crudagenda.util.toJson
import com.example.crudagenda.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListaAgendaFragment :
    BaseFragment<FragmentListaAgendaBinding>(R.layout.fragment_lista_agenda) {


    private val viewModel: ViewModelListaAgenda by viewModels()
    private val viewModelUpdate: ViewModelUpdate by viewModels()
    private val adapter =
        ListaAgendaAdapter(clickOnItem = { clickOnItem(it) }, clickOnCheck = { isCheck, note ->
            note.isComplete = isCheck
            viewModelUpdate.updateNote(note)
        })
    private var alertMessageDialog: AlertMessageDialog? = null

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = "Notas", showArrow = false, showSearchView = true
    )

    override fun configSearchView() = MainActivity.SearchViewConfig(showDeleteIcon = true,
        showSearchView = true,
        onQueryTextSubmit = {
            lifecycleScope.launch {
                adapter.setData(mutableListOf())
                viewModel.searchNotes("%$it%")
            }
        },
        clickOnDeleteIcon = {
            alertMessageDialog?.showAlertDialog("¿Estas seguro que deseas eliminar todos los contactos?")
        })


    override fun setUpUi() = with(binding) {
        recyclerViewContactos.adapter = adapter
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listaAgenda_to_addContact)
        }
        swipeRefreshLayout.setOnRefreshListener {
            adapter.setData(mutableListOf())
            swipeRefreshLayout.isRefreshing = false
            showProgress(true)
            lifecycleScope.launch {
                viewModel.getAllNotes()
            }
        }
        setUpAlertDialogDelete()
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.getAllNotesFlow().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                adapter.setData(mutableListOf())
                binding.tvNoData.visible()
            } else {
                binding.tvNoData.gone()
                adapter.setData(it.toMutableList())
            }
            showProgress(false)
        }
        viewModel.searchNotesResponse.observe(viewLifecycleOwner) {
            showProgress(it is ResultData.Loading)
            when (it) {
                is ResultData.Error -> {
                    showErrorApi(
                        messageBody = getString(R.string.error_db)
                    )
                }

                is ResultData.Success -> {
                    it.data?.let { notes ->
                        adapter.setData(mutableListOf())
                        adapter.setData(notes.toMutableList())
                    }
                }

                else -> {}
            }
        }
        viewModel.getAllNotesResponse.observe(viewLifecycleOwner) {
            showProgress(it is ResultData.Loading)
            when (it) {
                is ResultData.Error -> {
                    showErrorApi(
                        messageBody = getString(R.string.error_db)
                    )
                }

                is ResultData.Success -> {
                    it.data?.let { notes ->
                        adapter.setData(mutableListOf())
                        adapter.setData(notes.toMutableList())
                    }
                }

                else -> {}
            }
        }
    }

    private fun setUpAlertDialogDelete() {
        alertMessageDialog = AlertMessageDialog(requireContext(), object : ListenerAlertDialog {
            override fun btnCancel() {

            }

            override fun btnEliminar() {
                viewModel.deleteAllContacts()
            }

        })

    }

    private fun clickOnItem(note: Note) {
        findNavController().navigate(ListaAgendaFragmentDirections.actionListaAgendaToUpdate(note.toJson()))
    }

}