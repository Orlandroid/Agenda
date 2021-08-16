package com.example.crudagenda

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.crudagenda.adaptadores.AdaptadorContacto
import com.example.crudagenda.data.Contacto
import com.example.crudagenda.data.ContactoDatabase
import com.example.crudagenda.repositorio.ContactoRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.runBlocking


class ListaAgenda : Fragment() {

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var recyclerContactos: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_agenda, container, false)
        recyclerContactos = view.findViewById(R.id.recycler_view_contactos)
        floatingActionButton = view.findViewById(R.id.floating_action_button)
        val repository = ContactoRepository(requireContext())
        val contactos = runBlocking {
            repository.getAllContacs()
        }
        val adaptadorContacto = AdaptadorContacto(contactos)
        recyclerContactos.adapter = adaptadorContacto
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listaAgenda_to_addContact)
        }
        return view
    }

}