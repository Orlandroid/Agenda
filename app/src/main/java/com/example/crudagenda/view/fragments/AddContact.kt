package com.example.crudagenda.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentAddContactBinding
import com.example.crudagenda.viewmodel.ViewModelAddContact
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContact : Fragment() {


    private var _binding: FragmentAddContactBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ViewModelAddContact by viewModels()

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
        viewModel.insertContact(name, phone, birthday, note)
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