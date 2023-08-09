package com.example.crudagenda.ui.listaagenda

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentListaAgendaBinding
import com.example.crudagenda.ui.MainActivity
import com.example.crudagenda.ui.base.BaseFragment
import com.example.crudagenda.util.AlertMessageDialog
import com.example.crudagenda.util.ListenerAlertDialog
import com.example.crudagenda.util.gone
import com.example.crudagenda.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListaAgendaFragment :
    BaseFragment<FragmentListaAgendaBinding>(R.layout.fragment_lista_agenda), ListenerAlertDialog {


    private val viewModel: ViewModelListaAgenda by viewModels()
    private val adapter = ListaAgendaAdapter()

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = "My title",
        showArrow = false
    )

    private fun getListener(): ListenerAlertDialog = this

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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_lista_agenda, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.submenu_eliminar -> {
                val alert = AlertMessageDialog(requireContext(), getListener())
                alert.showAlertDialog("¿Estas seguro que deseas eliminar todos los contactos?")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun btnCancel() {

    }

    override fun btnEliminar() {
        viewModel.deleteAllContacts()
        binding.progressBar.visible()
    }

}