package com.example.crudagenda.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.data.Contacto
import com.example.crudagenda.databinding.FragmentAddContactBinding
import com.example.crudagenda.repositorio.ContactoRepository
import kotlinx.coroutines.*

class AddContact : Fragment() {


    private var _binding: FragmentAddContactBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContactBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.buttonInsertar.setOnClickListener {
            getValues()
        }
        binding.txtCumple.setOnClickListener {
            showDatePickerDialog()
        }
        return view
    }

    private fun getValues() {
        val name = binding.txtName.text.toString()
        val phone = binding.txtTelefono.text.toString()
        val birthday = binding.txtCumple.text.toString()
        val note = binding.txtNota.text.toString()
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
                binding.txtCumple.setText(selectedDate)
            }, requireContext())
        activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
    }

}