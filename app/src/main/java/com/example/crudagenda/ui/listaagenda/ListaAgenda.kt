package com.example.crudagenda.ui.listaagenda

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.ui.addcontact.ContactoAdapter
import com.example.crudagenda.databinding.FragmentListaAgendaBinding
import com.example.crudagenda.util.AlertMessageDialog
import com.example.crudagenda.util.ListenerAlertDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListaAgenda : Fragment(), ListenerAlertDialog {


    private var _binding: FragmentListaAgendaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ViewModelListaAgenda by viewModels()
    private val TAG = "LISTA_AGENDA"

    private fun getListener(): ListenerAlertDialog = this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaAgendaBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpRecyclerView()
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listaAgenda_to_addContact)
        }
        return view
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
                alert.showAlertDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerView() {
        viewModel.getAllContacts()
        viewModel.contactos.observe(viewLifecycleOwner, {
            binding.recyclerViewContactos.adapter = ContactoAdapter(it)
        })
    }

    override fun btnCancel() {
        Log.w(TAG, "CANCEL")
    }

    override fun btnEliminar() {
        Log.w(TAG, "Eliminado")
        viewModel.deleteAllContacts()
        setUpRecyclerView()
    }

}