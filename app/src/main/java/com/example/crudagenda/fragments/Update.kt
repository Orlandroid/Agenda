package com.example.crudagenda.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.navArgs
import com.example.crudagenda.R

class Update : Fragment() {

    private val args by navArgs<UpdateArgs>()
    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var birthday: EditText
    private lateinit var note: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        name = view.findViewById(R.id.update_txt_nombre)
        phone = view.findViewById(R.id.update_txt_phone)
        birthday = view.findViewById(R.id.update_txt_birthday)
        note = view.findViewById(R.id.update_txt_note)
        name.setText(args.currentContact.name)
        phone.setText(args.currentContact.phone)
        birthday.setText(args.currentContact.birthday)
        note.setText(args.currentContact.nota)
        return view
    }


}