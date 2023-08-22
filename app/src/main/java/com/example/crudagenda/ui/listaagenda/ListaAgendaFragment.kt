package com.example.crudagenda.ui.listaagenda

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentListaAgendaBinding
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.ui.MainActivity
import com.example.crudagenda.ui.base.BaseFragment
import com.example.crudagenda.util.AlertMessageDialog
import com.example.crudagenda.util.ListenerAlertDialog
import com.example.crudagenda.util.gone
import com.example.crudagenda.util.toJson
import com.example.crudagenda.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaAgendaFragment :
    BaseFragment<FragmentListaAgendaBinding>(R.layout.fragment_lista_agenda) {


    private val viewModel: ViewModelListaAgenda by viewModels()
    private val adapter = ListaAgendaAdapter(clickOnItem = { clickOnItem(it) })
    private var alertMessageDialog: AlertMessageDialog? = null

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = "My title", showArrow = false, showSearchView = true
    )

    override fun configSearchView() = MainActivity.SearchViewConfig(
        showDeleteIcon = true,
        showSearchView = true,
        onQueryTextChange = {
            //add search
        },
        onQueryTextSubmit = {
            //add search
        },
        clickOnDeleteIcon = {
            alertMessageDialog?.showAlertDialog("¿Estas seguro que deseas eliminar todos los contactos?")
        }
    )


    override fun setUpUi() = with(binding) {
        progressBar.visible()
        viewModel.getAllContacts().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                adapter.setData(mutableListOf())
                tvNoData.visible()
            } else {
                tvNoData.gone()
                adapter.setData(it.toMutableList())
            }
            progressBar.gone()
        }
        recyclerViewContactos.adapter = adapter
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listaAgenda_to_addContact)
        }
        setUpAlertDialogDelete()
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