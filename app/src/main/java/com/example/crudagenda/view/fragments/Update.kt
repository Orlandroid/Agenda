package com.example.crudagenda.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.crudagenda.R
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.databinding.FragmentUpdateBinding
import com.example.crudagenda.repositorio.ContactoRepository
import com.example.crudagenda.viewmodel.ViewModelUpdate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Update : Fragment() {

    private val args by navArgs<UpdateArgs>()
    private var _binding: FragmentUpdateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ViewModelUpdate by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root
        setDataArgs()
        binding.updateButton.setOnClickListener {
            updateContacto(getContact())
        }
        binding.updateTxtBirthday.setOnClickListener {
            showDatePickerDialog()
        }
        return view
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
                        viewModel.deleteContacto(args.currentContact)
                        Toast.makeText(
                            requireContext(),
                            "Se ha elimnado el contacto",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.i("DELETE", "Eliminado")
                        findNavController().navigate(R.id.action_update_to_listaAgenda)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                alert.create()
                alert.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getContact(): Contacto = Contacto(
        args.currentContact.id,
        binding.updateTxtNombre.text.toString(),
        binding.updateTxtPhone.text.toString(),
        binding.updateTxtBirthday.text.toString(),
        binding.updateTxtNote.text.toString()
    )


    private fun setDataArgs() {
        val contacto = args.currentContact
        binding.updateTxtNombre.setText(contacto.name)
        binding.updateTxtPhone.setText(contacto.phone)
        binding.updateTxtBirthday.setText(contacto.birthday)
        binding.updateTxtNote.setText(contacto.note)
    }

    private fun updateContacto(contacto: Contacto) {
        viewModel.updateContacto(contacto)
        Toast.makeText(requireContext(), "Actualizado", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_update_to_listaAgenda)
    }


    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                binding.updateTxtBirthday.setText(selectedDate)
            }, requireContext())
        activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
    }

}