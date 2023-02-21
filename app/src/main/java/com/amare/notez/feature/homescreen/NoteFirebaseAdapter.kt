package com.amare.notez.feature.homescreen

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.amare.notez.R
import com.amare.notez.core.domain.model.Note
import com.amare.notez.feature.createnote.CreateNoteActivity
import com.amare.notez.util.Constants
import com.amare.notez.util.Utils
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query
import kotlin.coroutines.coroutineContext

class NoteFirebaseAdapter(
    private val query: Query,
    private val context: Context
) : FirebaseRecyclerAdapter<Note, NoteFirebaseAdapter.NoteViewHolder>(
    FirebaseRecyclerOptions.Builder<Note>()
        .setQuery(query, Note::class.java)
        .build()
) {
    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.item_title)
        private val contentTextView: TextView = view.findViewById(R.id.item_note)

        fun bind(note: Note) {
            titleTextView.text = note.title
            contentTextView.text = note.note
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, note: Note) {
        holder.bind(note)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CreateNoteActivity::class.java)
            intent.putExtra(Constants.PASSING_DATA_NOTE, note)
            context.startActivity(intent)
        }
    }


}