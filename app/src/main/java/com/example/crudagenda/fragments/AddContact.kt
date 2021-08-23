package com.example.crudagenda.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.data.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import kotlinx.coroutines.*

class AddContact : Fragment() {

    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var birthday: EditText
    private lateinit var note: EditText
    private lateinit var buttonGetInto: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_contact, container, false)
        name = view.findViewById(R.id.txt_name)
        phone = view.findViewById(R.id.txt_telefono)
        birthday = view.findViewById(R.id.txt_cumple)
        note = view.findViewById(R.id.txt_nota)
        buttonGetInto = view.findViewById(R.id.button_insertar)
        buttonGetInto.setOnClickListener {
            getValues()
        }
        birthday.setOnClickListener {
            showDatePickerDialog()
        }
        return view
    }

    private fun getValues() {
        val name = name.text.toString()
        val phone = phone.text.toString()
        val birthday = birthday.text.toString()
        val note = note.text.toString()
        insertContact(name, phone, birthday, note)
    }

    private fun insertContact(name: String, phone: String, birthday: String, note: String) {
        val contact = Contacto(0, name, phone, birthday, note)
        val repository = ContactoRepository(requireContext())
        runBlocking {
            repository.addContacto(contact)
            Toast.makeText(requireContext(), "Contacto agregado", Toast.LENGTH_SHORT).show()
        }
        findNavController().navigate(R.id.action_addContact_to_listaAgenda)
    }

    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                birthday.setText(selectedDate)
            }, requireContext())
        activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
    }

}