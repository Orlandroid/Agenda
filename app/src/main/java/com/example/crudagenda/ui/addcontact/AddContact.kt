package com.example.crudagenda.ui.addcontact

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentAddContactBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.crudagenda.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


@AndroidEntryPoint
class AddContact : Fragment() {


    private var _binding: FragmentAddContactBinding? = null

    private val binding get() = _binding!!
    private val viewModel: ViewModelAddContact by viewModels()
    private var imageUri: Uri? = null
    private var imageBase64: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddContactBinding.inflate(layoutInflater, container, false)
        setUpUi()
        setUpObserver()
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

    private fun setUpObserver() {
        viewModel.progresBar.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.progressBar3.visibility = View.VISIBLE
                }
                false -> {
                    binding.progressBar3.visibility = View.INVISIBLE
                }
            }
        }
    }


    private fun doOnTextChange() {
        with(binding) {
            txtName.editText?.doOnTextChanged { _, _, _, _ ->
                binding.buttonInsertar.isEnabled = !areEmptyFields()
            }
            txtTelefono.editText?.doOnTextChanged { _, _, _, _ ->
                binding.buttonInsertar.isEnabled = !areEmptyFields()
            }
            txtNota.editText?.doOnTextChanged { _, _, _, _ ->
                binding.buttonInsertar.isEnabled = !areEmptyFields()
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
                val path = getPath(requireContext(), imageUri)
                if (path != null) {
                    imageBase64 = path
                }
                Log.w("imageURI", imageUri.toString())
            }
        }


    private fun getValues() {
        Log.w("IMAGEN", imageBase64)
        val name = binding.txtName.editText?.text.toString()
        val phone = binding.txtTelefono.editText?.text.toString()
        val birthday = binding.txtCumple.editText?.text.toString()
        val note = binding.txtNota.editText?.text.toString()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.insertContact(name, phone, birthday, note, imageUri.toString())
            findNavController().popBackStack()
        }
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