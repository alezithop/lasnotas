package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sacg.lasnotas.R
import models.Note

class NotesAdapter (private val mNotes: List<Note>): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val noteTextView = itemView.findViewById<TextView>(R.id.tvNotePreview)
        val noteDateTextView = itemView.findViewById<TextView>(R.id.tvNoteDate)
        val editButton = itemView.findViewById<Button>(R.id.btnEditNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val noteView = inflater.inflate(R.layout.item_note, parent, false)

        return ViewHolder(noteView)
    }

    override fun getItemCount(): Int {
        return mNotes.size
    }

    override fun onBindViewHolder(viewHolder: NotesAdapter.ViewHolder, position: Int) {
        val note: Note = mNotes.get(position)
        val textView = viewHolder.noteTextView
        textView.setText(note.noteContent)

        val labelDate = viewHolder.noteDateTextView
        labelDate.text = note.createdDate

        val button = viewHolder.editButton
        button.text = if (note.isDeleted) "(Deleted)" else "Edit >"
        button.isEnabled = !note.isDeleted
    }
}