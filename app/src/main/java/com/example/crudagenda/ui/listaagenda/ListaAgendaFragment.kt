package com.example.crudagenda.ui.listaagenda

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentListaAgendaBinding

import com.example.crudagenda.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListaAgendaFragment : Fragment(), ListenerAlertDialog {


    private var _binding: FragmentListaAgendaBinding? = null

    private val binding get() = _binding!!
    private val viewModel: ViewModelListaAgenda by viewModels()
    private val adapter = ListaAgendaAdapter()

    private fun getListener(): ListenerAlertDialog = this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaAgendaBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() = with(binding) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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