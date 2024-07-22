package com.example.notesapptask.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.notesapptask.R
import com.example.notesapptask.Database.Note


class NotesAdapter(private var notesList: List<Note>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_notes, parent, false)
        return ViewHolder(view)
    }

    fun setNotes(notes: List<Note>) {
        notesList = notes
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id_notes_title.text = notesList[position].notesTitle
        holder.id_notes_desc.text = notesList[position].notesDesc
        // Determine which image to display based on position modulo 4
        val imageResource = when (position % 3) {
            0 -> R.drawable.bg_card_notes
            1 -> R.drawable.bg_card_notes2
            2 -> R.drawable.bg_card_notes3
            else -> R.drawable.bg_card_notes2// Handle any unexpected cases
        }
        holder.id_card_notes.setBackgroundResource(imageResource)
        holder.id_edit.setOnClickListener {
            onItemClickListener.editNotes(notesList[position])
        }
        holder.id_delete.setOnClickListener {
            onItemClickListener.deleteNotes(notesList[position])
        }
    }

    override fun getItemCount(): Int = notesList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id_notes_title: TextView = itemView.findViewById(R.id.id_notes_title)
        val id_notes_desc: TextView = itemView.findViewById(R.id.id_notes_desc)
        val id_card_notes: RelativeLayout = itemView.findViewById(R.id.id_card_notes)
        val id_delete: ImageView = itemView.findViewById(R.id.id_delete)
        val id_edit: ImageView = itemView.findViewById(R.id.id_edit)
    }


}
interface OnItemClickListener {
    fun editNotes(obj: Note)
    fun deleteNotes(obj: Note)
}




