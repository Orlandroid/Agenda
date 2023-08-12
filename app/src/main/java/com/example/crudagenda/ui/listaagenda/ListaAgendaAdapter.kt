package com.example.crudagenda.ui.listaagenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.crudagenda.R
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.util.click

class ListaAgendaAdapter :
    RecyclerView.Adapter<ListaAgendaAdapter.ViewHolder>() {

    private var listaContactos = mutableListOf<Note>()

    fun setData(notes: MutableList<Note>) {
        listaContactos = notes
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val description: TextView = view.findViewById(R.id.description)

        fun bind(note: Note) {
            title.text = note.title
            description.text = note.description
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_note, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val contacto = listaContactos[position]
        viewHolder.bind(contacto)
        viewHolder.itemView.click {
            val accion =
                ListaAgendaFragmentDirections.actionListaAgendaToUpdate(contacto.toString())
            viewHolder.itemView.findNavController().navigate(accion)
        }
    }

    override fun getItemCount() = listaContactos.size

}
