package com.example.crudagenda.ui.listaagenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crudagenda.R
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.db.modelo.Priority
import com.example.crudagenda.util.click


class ListaAgendaAdapter(
    private val clickOnItem: (Note) -> Unit,
    private val clickOnCheck: (Boolean, Note) -> Unit
) :
    RecyclerView.Adapter<ListaAgendaAdapter.ViewHolder>() {

    private var listaContactos = mutableListOf<Note>()

    fun setData(notes: MutableList<Note>) {
        listaContactos = notes
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val description: TextView = view.findViewById(R.id.description)
        private val view: View = view.findViewById(R.id.view)
        private val check: CheckBox = view.findViewById(R.id.checkbox)

        fun bind(note: Note, clickOnCheck: (Boolean, Note) -> Unit) {
            title.text = note.title
            description.text = note.description
            view.setBackgroundColor(itemView.context.resources.getColor(getPriorityColor(note.priority)))
            check.isChecked = note.isComplete
            check.setOnCheckedChangeListener { _, isChecked ->
                clickOnCheck(isChecked, note)
            }

        }

        private fun getPriorityColor(priority: Priority): Int {
            return when (priority) {
                Priority.LOW -> {
                    R.color.low_priority
                }

                Priority.MEDIUM -> {
                    R.color.medium_priority
                }

                Priority.HIGH -> {
                    R.color.higth_priority
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_note, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val contacto = listaContactos[position]
        viewHolder.bind(contacto, clickOnCheck)
        viewHolder.itemView.click {
            clickOnItem(contacto)
        }
    }

    override fun getItemCount() = listaContactos.size

}
