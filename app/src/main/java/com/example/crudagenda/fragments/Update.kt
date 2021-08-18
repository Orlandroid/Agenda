package com.example.crudagenda.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.crudagenda.R
import com.example.crudagenda.data.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Update : Fragment() {

    private val args by navArgs<UpdateArgs>()
    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var birthday: EditText
    private lateinit var note: EditText
    private lateinit var buttonUpdate: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        name = view.findViewById(R.id.update_txt_nombre)
        phone = view.findViewById(R.id.update_txt_phone)
        birthday = view.findViewById(R.id.update_txt_birthday)
        note = view.findViewById(R.id.update_txt_note)
        buttonUpdate = view.findViewById(R.id.update_button)
        setDataArgs()
        buttonUpdate.setOnClickListener {
            updateContacto(getContact())
        }
        birthday.setOnClickListener {
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
                        val repository = ContactoRepository(requireContext())
                        GlobalScope.launch(Dispatchers.IO) {
                            repository.deleteContacto(args.currentContact)
                        }
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
        name.text.toString(),
        phone.text.toString(),
        birthday.text.toString(),
        note.text.toString()
    )


    private fun setDataArgs() {
        val contacto = args.currentContact
        name.setText(contacto.name)
        phone.setText(contacto.phone)
        birthday.setText(contacto.birthday)
        note.setText(contacto.note)
    }

    private fun updateContacto(contacto: Contacto) {
        val repository = ContactoRepository(requireContext())
        GlobalScope.launch(Dispatchers.IO) {
            repository.updateContact(contacto)
        }
        Toast.makeText(requireContext(), "Actualizado", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_update_to_listaAgenda)
    }


    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                birthday.setText(selectedDate)
            }, requireContext())
        activity?.let { newFragment.show(it.supportFragmentManager, "datePicker") }
    }

}