package com.example.crudagenda

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crudagenda.data.Contacto
import com.example.crudagenda.data.ContactoDatabase
import com.example.crudagenda.repositorio.ContactoRepository
import kotlinx.coroutines.runBlocking


class ListaAgenda : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_agenda, container, false)
        val repository = ContactoRepository(requireContext())
        return view
    }

}