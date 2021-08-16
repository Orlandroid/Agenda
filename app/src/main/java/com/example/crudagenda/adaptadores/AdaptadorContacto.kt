package com.example.crudagenda.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crudagenda.R
import com.example.crudagenda.data.Contacto

class AdaptadorContacto(private val contacto: List<Contacto>) :
    RecyclerView.Adapter<AdaptadorContacto.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_nombre)
        val phone: TextView = view.findViewById(R.id.item_telefono)
        val birthday: TextView = view.findViewById(R.id.item_cumple)
        val note: TextView = view.findViewById(R.id.item_nota)

        fun bind(contact: Contacto) {
            name.text = contact.name
            phone.text = contact.phone
            birthday.text = contact.birthday
            note.text = contact.nota
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_contacto, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val contacto = contacto[position]
        viewHolder.bind(contacto)
    }

    override fun getItemCount() = contacto.size

}
