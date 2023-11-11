package com.example.crudagenda.ui.listaagenda

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentListaAgendaBinding
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.db.modelo.Priority
import com.example.crudagenda.db.modelo.Priority.HIGH
import com.example.crudagenda.db.modelo.Priority.LOW
import com.example.crudagenda.db.modelo.Priority.MEDIUM
import com.example.crudagenda.ui.MainActivity
import com.example.crudagenda.ui.base.BaseFragment
import com.example.crudagenda.util.AlertMessageDialog
import com.example.crudagenda.util.ListenerAlertDialog
import com.example.crudagenda.util.createAPopMenu
import com.example.crudagenda.util.gone
import com.example.crudagenda.util.observeResultGenericDb
import com.example.crudagenda.util.showInfoMessage
import com.example.crudagenda.util.showProgress
import com.example.crudagenda.util.toJson
import com.example.crudagenda.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListaAgendaFragment :
    BaseFragment<FragmentListaAgendaBinding>(R.layout.fragment_lista_agenda) {


    private val viewModel: ViewModelListaAgenda by viewModels()
    private val adapter = ListaAgendaAdapter(clicksOnNote())

    private var alertMessageDialog: AlertMessageDialog? = null

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = "Notas", showArrow = false, showSearchView = true
    )

    private fun clicksOnNote() = ClicksNote(onClickOnItem = { note ->
        clickOnItem(note)
    }, onClickOnCheck = { check, note ->
        clickOnCheck(check, note)
    }, onClickOnDelete = { note ->
        clickOnDelete(note)
    })

    override fun configSearchView() = MainActivity.SearchViewConfig(showDeleteIcon = true,
        showSearchView = true,
        onQueryTextSubmit = {
            adapter.setData(mutableListOf())
            viewModel.searchNotes("%$it%")
        },
        clickOnDeleteIcon = {
            alertMessageDialog?.showAlertDialog("¿Estas seguro que deseas eliminar todas las notas?")
        },
        clickOnFilter = {
            clickOnFilter(it)
        })

    private fun clickOnFilter(filterImage: View) {
        filterImage.createAPopMenu(
            getListPriority()
        ) { position ->
            val priority = getPriority(position)
            adapter.setData(arrayListOf())
            if (priority == null) {
                //viewModel.getAllNotesFlow()
            } else {
                viewModel.getNotesByPriority(priority.name)
            }
        }
    }

    private fun getPriority(position: Int): Priority? {
        when (position) {
            0 -> {
                return LOW
            }

            1 -> {
                return MEDIUM
            }

            2 -> {
                return HIGH
            }

            else -> {
                return null
            }
        }
    }

    private fun getListPriority() = listOf(
        "Low priority", "Normal priority", "High priority", "All"
    )


    override fun setUpUi() = with(binding) {
        recyclerViewContactos.adapter = adapter
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listaAgenda_to_addContact)
        }
        swipeRefreshLayout.setOnRefreshListener {
            adapter.setData(mutableListOf())
            swipeRefreshLayout.isRefreshing = false
            showProgress(true)
            //viewModel.getAllNotesFlow()
        }
        setUpAlertDialogDelete()
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAllNotesFlow().collect {
                if (it.isEmpty()) {
                    adapter.setData(mutableListOf())
                    binding.tvNoData.visible()
                } else {
                    binding.tvNoData.gone()
                    adapter.setData(it.toMutableList())
                }
                showProgress(false)
            }
        }
        observeResultGenericDb(viewModel.searchNotesResponse) {
            it?.let { notes ->
                adapter.setData(mutableListOf())
                adapter.setData(notes.toMutableList())
            }
        }
        observeResultGenericDb(viewModel.getAllNotesResponse) {
            it?.let { notes ->
                adapter.setData(mutableListOf())
                adapter.setData(notes.toMutableList())
            }
        }
        observeResultGenericDb(viewModel.getAllNotesByPriorityResponse) {
            it?.let { notes ->
                adapter.setData(mutableListOf())
                adapter.setData(notes.toMutableList())
            }
        }

    }

    private fun setUpAlertDialogDelete() {
        alertMessageDialog = AlertMessageDialog(requireContext(), object : ListenerAlertDialog {
            override fun btnCancel() {

            }

            override fun btnEliminar() {
                viewModel.deleteAllNotes()
            }

        })
    }

    private fun clickOnItem(note: Note) {
        findNavController().navigate(ListaAgendaFragmentDirections.actionListaAgendaToUpdate(note.toJson()))
    }

    private fun clickOnCheck(check: Boolean, note: Note) {

    }

    private fun clickOnDelete(note: Note) {
        showInfoMessage(
            message = getString(R.string.delete_ask), isTwoButtonDialog = true
        ) {
            viewModel.deleteNote(note)
        }
    }

}