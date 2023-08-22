package com.example.crudagenda.ui.addcontact

import android.net.Uri
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentAddNoteBinding
import com.example.crudagenda.db.modelo.Priority
import com.example.crudagenda.ui.MainActivity
import com.example.crudagenda.ui.base.BaseFragment
import com.example.crudagenda.util.click
import com.example.crudagenda.util.hideKeyboard
import com.example.crudagenda.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddContactFragment : BaseFragment<FragmentAddNoteBinding>(R.layout.fragment_add_note) {


    private val viewModel: ViewModelAddContact by viewModels()
    private var imageUri: Uri? = null

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.agregar_contacto)
    )

    override fun setUpUi() = with(binding) {
        doOnTextChange()
        rootView.click {
            hideKeyboard()
        }
        buttonInsertar.click {
            saveNote()
        }
        setUpAutocomplete()
    }

    private fun setUpAutocomplete() = with(binding) {
        val priorityList = arrayOf("Low", "Normal", "High")
        val adapter = ArrayAdapter(
            requireContext(), R.layout.support_simple_spinner_dropdown_item, priorityList
        )
        autoCompletePriority.setAdapter(adapter)
        autoCompletePriority.setText(priorityList[0], false)
    }

    override fun observerViewModel() {
        viewModel.progresBar.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    //binding.progressBar3.visible()
                }

                false -> {
                    //binding.progressBar3.invisible()
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
            title.editText?.doOnTextChanged { _, _, _, _ ->
                binding.buttonInsertar.isEnabled = !areEmptyFields()
            }
            description.editText?.doOnTextChanged { _, _, _, _ ->
                binding.buttonInsertar.isEnabled = !areEmptyFields()
            }
        }
    }

    private fun areEmptyFields(): Boolean {
        val nombreIsEmpty = binding.title.editText?.text.toString().trim().isEmpty()
        val telefonoIsEmpty = binding.description.editText?.text.toString().trim().isEmpty()
        return nombreIsEmpty or telefonoIsEmpty
    }

    private fun getPriority(): Priority {
        return when (binding.autoCompletePriority.text.toString()) {
            "Low" -> {
                Priority.LOW
            }

            "Normal" -> {
                Priority.MEDIUM
            }

            else -> {
                Priority.HIGH
            }
        }
    }


    private fun saveNote() {
        val title = binding.title.editText?.text.toString()
        val description = binding.description.editText?.text.toString()
        lifecycleScope.launch {
            viewModel.insertNote(
                title = title, description = description, priority = getPriority()
            )
        }
        findNavController().popBackStack()
    }
}