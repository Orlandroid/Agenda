package com.example.crudagenda.ui.listaagenda

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crudagenda.R
import com.example.crudagenda.modelo.Contacto

class ListaAgendaAdapter() :
    RecyclerView.Adapter<ListaAgendaAdapter.ViewHolder>() {

    private var listaContactos = listOf<Contacto>()

    fun setData(contatos: List<Contacto>) {
        listaContactos = contatos
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_nombre)
        val phone: TextView = view.findViewById(R.id.item_telefono)
        val birthday: TextView = view.findViewById(R.id.item_cumple)
        val note: TextView = view.findViewById(R.id.item_nota)
        val image: ImageView = view.findViewById(R.id.imageContacto)

        fun bind(contact: Contacto) {
            name.text = contact.name
            phone.text = contact.phone
            birthday.text = contact.birthday
            note.text = contact.note
            Glide.with(itemView.context)
                .load(contact.image)
                .into(image)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_contacto, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val contacto = listaContactos[position]
        viewHolder.bind(contacto)
        viewHolder.itemView.setOnClickListener {
            val accion = ListaAgendaDirections.actionListaAgendaToUpdate(contacto)
            viewHolder.itemView.findNavController().navigate(accion)
        }
    }

    override fun getItemCount() = listaContactos.size

}
