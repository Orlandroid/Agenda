package com.example.crudagenda.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.crudagenda.R
import com.example.crudagenda.data.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Update : Fragment() {

    private val args by navArgs<UpdateArgs>()
    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var birthday: EditText
    private lateinit var note: EditText
    private lateinit var buttonUpdate: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        name = view.findViewById(R.id.update_txt_nombre)
        phone = view.findViewById(R.id.update_txt_phone)
        birthday = view.findViewById(R.id.update_txt_birthday)
        note = view.findViewById(R.id.update_txt_note)
        buttonUpdate = view.findViewById(R.id.update_button)
        setDataArgs()
        buttonUpdate.setOnClickListener {
            updateContacto(getContact())
        }
        birthday.setOnClickListener {
            showDatePickerDialog()
        }
        return view
    }


    private fun getContact(): Contacto = Contacto(
        args.currentContact.id,
        name.text.toString(),
        phone.text.toString(),
        birthday.text.toString(),
        note.text.toString()
    )


    private fun setDataArgs() {
        val contacto = args.currentContact
        name.setText(contacto.name)
        phone.setText(contacto.phone)
        birthday.setText(contacto.birthday)
        note.setText(contacto.note)
    }

    private fun updateContacto(contacto: Contacto) {
        val repository = ContactoRepository(requireContext())
        GlobalScope.launch(Dispatchers.IO) {
            repository.updateContact(contacto)
        }
        Toast.makeText(requireContext(), "Actualizado", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_update_to_listaAgenda)
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