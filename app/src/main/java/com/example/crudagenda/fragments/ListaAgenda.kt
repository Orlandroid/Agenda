package com.example.crudagenda.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.adaptadores.AdaptadorContacto
import com.example.crudagenda.databinding.FragmentListaAgendaBinding
import com.example.crudagenda.repositorio.ContactoRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*


class ListaAgenda : Fragment() {


    private var _binding: FragmentListaAgendaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


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
                val alert = MaterialAlertDialogBuilder(requireContext())
                alert.setTitle("Confirmacion")
                    .setMessage("¿ Estas seguro que deseas eliminar todos los registros ?")
                    .setPositiveButton("Eliminar") { dialog, _ ->
                        val repository = ContactoRepository(requireContext())
                        GlobalScope.launch(Dispatchers.IO) {
                            repository.deleteAllContactos()
                        }
                        setUpRecyclerView()
                        Toast.makeText(
                            requireContext(),
                            "Se ha elimnado todos los contactos",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                alert.create()
                alert.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerView() {
        val repository = ContactoRepository(requireContext())
        GlobalScope.launch(Dispatchers.IO) {
            val contactos = async { repository.getAllContacs() }
            val adaptadorContacto = AdaptadorContacto(contactos.await())
            withContext(Dispatchers.Main) {
                binding.recyclerViewContactos.adapter = adaptadorContacto
            }
        }
    }

}