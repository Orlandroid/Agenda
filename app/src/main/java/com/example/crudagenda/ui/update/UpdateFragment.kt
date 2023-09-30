package com.example.crudagenda.ui.update

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentUpdateBinding
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.db.modelo.Priority
import com.example.crudagenda.ui.MainActivity
import com.example.crudagenda.ui.base.BaseFragment
import com.example.crudagenda.util.ResultData
import com.example.crudagenda.util.click
import com.example.crudagenda.util.fromJson
import com.example.crudagenda.util.showErrorApi
import com.example.crudagenda.util.showProgress
import com.example.crudagenda.util.showSuccessMessage
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateFragment : BaseFragment<FragmentUpdateBinding>(R.layout.fragment_update) {

    private val args by navArgs<UpdateFragmentArgs>()

    private val viewModel: ViewModelUpdate by viewModels()
    private val priorityList = arrayOf("Low", "Normal", "High")

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.editar_contacto)
    )

    override fun setUpUi() {
        setDataArgs(args.note.fromJson())
        binding.buttonUpdate.click {
            updateNote(getNote())
        }
        setUpAutocomplete()
    }

    private fun setUpAutocomplete() = with(binding) {
        val adapter = ArrayAdapter(
            requireContext(), R.layout.support_simple_spinner_dropdown_item, priorityList
        )
        autoCompletePriority.setAdapter(adapter)
    }

    private fun setDataArgs(note: Note) = with(binding) {
        edTitle.setText(note.title)
        edDescription.setText(note.description)
        autoCompletePriority.setText(getStatus(note.priority.name))
    }

    override fun observerViewModel() {
        super.observerViewModel()
        viewModel.updateNoteResponse.observe(viewLifecycleOwner) {
            showProgress(it is ResultData.Loading)
            when (it) {
                is ResultData.Error -> {
                    showErrorApi(
                        messageBody = "Error al editar la nota", shouldCloseTheViewOnApiError = true
                    )
                }

                is ResultData.Success -> {
                    showSuccessMessage {
                        findNavController().popBackStack()
                    }
                }

                else -> {}
            }

        }
    }


    private fun getStatus(statusDb: String): String {
        return when (statusDb) {
            "LOW" -> {
                "Low"
            }

            "MEDIUM" -> {
                "Normal"
            }

            "HIGH" -> {
                "High"
            }

            else -> {
                ""
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_lista_agenda, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.submenu_eliminar -> {
                val alert = MaterialAlertDialogBuilder(requireContext())
                alert.setTitle("Confirmacion")
                    .setMessage("¿ Estas seguro que deseas eliminar el contacto ?")
                    .setPositiveButton("Eliminar") { dialog, _ ->
                        //viewModel.deleteNote(args.currentContact)
                        Toast.makeText(
                            requireContext(), "Se ha elimnado el contacto", Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                        dialog.dismiss()
                    }.setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                alert.create()
                alert.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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


    private fun getNote() = Note(
        id = args.note.fromJson<Note>().id,
        title = binding.title.editText?.text.toString(),
        description = binding.edDescription.text.toString(),
        priority = getPriority()
    )


    private fun updateNote(note: Note) {
        lifecycleScope.launch {
            viewModel.updateNote(note)
        }
    }

}