package com.example.crudagenda.ui.update

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentUpdateBinding
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.ui.MainActivity
import com.example.crudagenda.ui.base.BaseFragment
import com.example.crudagenda.util.click
import com.example.crudagenda.util.fromJson
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateFragment : BaseFragment<FragmentUpdateBinding>(R.layout.fragment_update) {

    private val args by navArgs<UpdateFragmentArgs>()

    private val viewModel: ViewModelUpdate by viewModels()
    private var imageUri: Uri? = null
    private val priorityList = arrayOf("Low", "Normal", "High")

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true, toolbarTitle = getString(R.string.editar_contacto)
    )

    override fun setUpUi() {
        setDataArgs(args.note.fromJson())
        binding.buttonUpdate.click {

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

    /*
    private fun getContact(): Contacto = Contacto(
        args.currentContact.id,
        binding.txtName.editText!!.text.toString(),
        binding.txtTelefono.editText!!.text.toString(),
        binding.txtCumple.editText!!.text.toString(),
        binding.txtNota.editText!!.text.toString().trim(),
        binding.imagen.getImageLikeBitmap()
    )*/


    private fun updateNote(note: Note) {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.updateNote(note)
        }
        Toast.makeText(requireContext(), "Actualizado", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                imageUri = data?.data
                //binding.imagen.setImageURI(imageUri)
            }
        }

}