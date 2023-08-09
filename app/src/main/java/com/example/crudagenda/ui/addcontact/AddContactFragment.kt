package com.example.crudagenda.ui.addcontact

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentAddContactBinding
import com.example.crudagenda.ui.MainActivity
import com.example.crudagenda.ui.base.BaseFragment
import com.example.crudagenda.util.DatePickerFragment
import com.example.crudagenda.util.getImageLikeBitmap
import com.example.crudagenda.util.hideKeyboard
import com.example.crudagenda.util.invisible
import com.example.crudagenda.util.openGaleryToChoseImage
import com.example.crudagenda.util.showToast
import com.example.crudagenda.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddContactFragment : BaseFragment<FragmentAddContactBinding>(R.layout.fragment_add_contact) {


    private val viewModel: ViewModelAddContact by viewModels()
    private var imageUri: Uri? = null

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.agregar_contacto)
    )

    override fun setUpUi() {
        doOnTextChange()
        binding.rootView.setOnClickListener {
            hideKeyboard()
        }
        binding.imagen.setOnClickListener {
            openGaleryToChoseImage(resultLauncher)
        }
        binding.buttonInsertar.setOnClickListener {
            getValues()
        }
        binding.txtCumple.setEndIconOnClickListener {
            showDatePickerDialog()
        }
        Glide.with(requireActivity()).load(R.drawable.unknown).circleCrop().into(binding.imagen)
    }

    override fun observerViewModel() {
        viewModel.progresBar.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.progressBar3.visible()
                }

                false -> {
                    binding.progressBar3.invisible()
                }
            }
            viewModel.isUpateContact.observe(viewLifecycleOwner) { isUpdate ->
                if (isUpdate) {
                    context?.showToast("Contacto actualizado con exito")
                } else {
                    context?.showToast("Error al actualizar al contacto")
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
        val birthdayIsEmpty = binding.txtCumple.editText?.text.toString().trim().isEmpty()
        return nombreIsEmpty or telefonoIsEmpty or birthdayIsEmpty
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
        var note = binding.txtNota.editText?.text.toString().trim()
        if (note.isEmpty()) {
            note = ""
        }
        lifecycleScope.launch {
            viewModel.insertContact(
                name = name,
                phone = phone,
                birthday = birthday,
                note = note,
                image = binding.imagen.getImageLikeBitmap()
            )
        }
        findNavController().popBackStack()

    }


    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance({ _, year, month, day ->
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                binding.txtCumple.editText?.setText(selectedDate)
                binding.buttonInsertar.isEnabled = !areEmptyFields()
            }, requireContext())
        activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
    }


}