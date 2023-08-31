package com.example.crudagenda.ui.listaagenda

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudagenda.R
import com.example.crudagenda.databinding.ItemNoteBinding
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.db.modelo.Priority
import com.example.crudagenda.util.click


data class ClicksNote(
    val onClickOnItem: (Note) -> Unit,
    val onClickOnCheck: (Boolean, Note) -> Unit,
    val onClickOnDelete: (Note) -> Unit
)

class ListaAgendaAdapter(
    private val clicks: ClicksNote,
) :
    RecyclerView.Adapter<ListaAgendaAdapter.ViewHolder>() {

    private var listaContactos = mutableListOf<Note>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(notes: MutableList<Note>) {
        listaContactos = notes
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, clicks: ClicksNote) = with(binding) {
            title.text = note.title
            description.text = note.description
            checkbox.isChecked = note.isComplete
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                clicks.onClickOnCheck(isChecked, note)
            }
            imageDelete.click {
                clicks.onClickOnDelete(note)
            }
            root.click {
                clicks.onClickOnItem(note)
            }
            val strokeColor = itemView.context.resources.getColor(getPriorityColor(note.priority))
            cardView.strokeColor = strokeColor
            view.setBackgroundColor(strokeColor)
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
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemNoteBinding.inflate(layoutInflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val contacto = listaContactos[position]
        viewHolder.bind(contacto, clicks)
    }

    override fun getItemCount() = listaContactos.size

}
