package com.example.crudagenda.ui.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.crudagenda.R
import com.example.crudagenda.databinding.FragmentUpdateBinding
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.util.DatePickerFragment
import com.example.crudagenda.util.getImageLikeBitmap
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null

    private val binding get() = _binding!!
    private val viewModel: ViewModelUpdate by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root
        setDataArgs()
        binding.buttonUpdate.setOnClickListener {
            updateContacto(getContact())
        }
        binding.txtCumple.setOnClickListener {
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
                        lifecycleScope.launch {
                            viewModel.deleteContacto(args.currentContact)
                        }
                        Toast.makeText(
                            requireContext(),
                            "Se ha elimnado el contacto",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
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
        binding.txtName.editText!!.text.toString(),
        binding.txtTelefono.editText!!.text.toString(),
        binding.txtCumple.editText!!.text.toString(),
        binding.txtNota.editText!!.text.toString().trim(),
        binding.imagen.getImageLikeBitmap()
    )


    private fun setDataArgs() = with(binding){
        val contacto = args.currentContact
        txtName.editText!!.setText(contacto.name)
        txtTelefono.editText!!.setText(contacto.phone)
        txtCumple.editText!!.setText(contacto.birthday)
        txtNota.editText!!.setText(contacto.note)
        Glide.with(requireActivity()).load(args.currentContact.image).into(imagen)
    }

    private fun updateContacto(contacto: Contacto) {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.updateContacto(contacto)
        }
        Toast.makeText(requireContext(), "Actualizado", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }


    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance({ _, year, month, day ->
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                binding.txtCumple.editText!!.setText(selectedDate)
            }, requireContext())
        activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
    }

}