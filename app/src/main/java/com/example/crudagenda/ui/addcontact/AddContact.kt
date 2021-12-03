package com.example.crudagenda.ui.addcontact

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentAddContactBinding
import com.example.crudagenda.util.DatePickerFragment
import com.example.crudagenda.util.hideKeyboard
import com.example.crudagenda.util.openGaleryToChoseImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContact : Fragment() {


    private var _binding: FragmentAddContactBinding? = null

    private val binding get() = _binding!!
    private val viewModel: ViewModelAddContact by viewModels()
    private var imageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContactBinding.inflate(layoutInflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        doOnTextChange()
        binding.rootView.setOnClickListener {
            hideKeyboard()
        }
        binding.btnImage.setOnClickListener {
            openGaleryToChoseImage(resultLauncher)
        }
        binding.buttonInsertar.setOnClickListener {
            getValues()
        }
        binding.txtCumple.setEndIconOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun doOnTextChange(){
        with(binding){
            txtName.editText?.doOnTextChanged { _, _, _, _ ->
                binding.buttonInsertar.isEnabled=!areEmptyFields()
            }
            txtTelefono.editText?.doOnTextChanged { _, _, _, _ ->
                binding.buttonInsertar.isEnabled=!areEmptyFields()
            }
            txtNota.editText?.doOnTextChanged { _, _, _, _ ->
                binding.buttonInsertar.isEnabled=!areEmptyFields()
            }
        }
    }

    private fun areEmptyFields(): Boolean {
        val nombreIsEmpty = binding.txtName.editText?.text.toString().trim().isEmpty()
        val telefonoIsEmpty = binding.txtTelefono.editText?.text.toString().trim().isEmpty()
        val notaIsEmpty = binding.txtNota.editText?.text.toString().trim().isEmpty()
        return nombreIsEmpty or telefonoIsEmpty or notaIsEmpty
    }


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                imageUri = data?.data
                binding.imagen.setImageURI(imageUri)
            }
        }


    private fun getValues() {
        val name = binding.txtName.editText?.text.toString()
        val phone = binding.txtTelefono.editText?.text.toString()
        val birthday = binding.txtCumple.editText?.text.toString()
        val note = binding.txtNota.editText?.text.toString()
        viewModel.insertContact(name, phone, birthday, note)
        findNavController().navigate(R.id.action_addContact_to_listaAgenda)
    }


    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                binding.txtCumple.editText?.setText(selectedDate)
            }, requireContext())
        activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}