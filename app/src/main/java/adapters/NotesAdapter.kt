package adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.sacg.lasnotas.NewNoteActivity
import com.sacg.lasnotas.R
// import models._NoteMockup
import models.NoteModel
// import java.text.SimpleDateFormat

class NotesAdapter (private val mNote: List<NoteModel>, private val cellClickListener: CellClickListener): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val noteTitleTextView = itemView.findViewById<TextView>(R.id.tvNoteTitle)
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
        return mNote.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(viewHolder: NotesAdapter.ViewHolder, position: Int) {
        val note: NoteModel = mNote.get(position)
        val textViewTitle = viewHolder.noteTitleTextView
        textViewTitle.setText(note.title)
        val textView = viewHolder.noteTextView
        textView.setText(note.content)

        val labelDate = viewHolder.noteDateTextView
        var date = note.createdDate
        labelDate.text = date

        val button = viewHolder.editButton
        if (note.isDeleted == 0) {
            button.text = ">"
            button.isEnabled = true
        } else {
            button.text = "(Deleted)"
            button.isEnabled = false
        }
        viewHolder.itemView.setOnClickListener{
            cellClickListener.onCellClickListener(note)
        }
    }
}

/*
class NotesAdapter (private val mNoteMockups: List<_NoteMockup>): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

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
        return mNoteMockups.size
    }

    override fun onBindViewHolder(viewHolder: NotesAdapter.ViewHolder, position: Int) {
        val noteMockup: _NoteMockup = mNoteMockups.get(position)
        val textView = viewHolder.noteTextView
        textView.setText(noteMockup.noteContent)

        val labelDate = viewHolder.noteDateTextView
        var date = noteMockup.createdDate
        val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
        val answer: String = formatter.format(date)
        Log.d("answer", answer)
//        labelDate.text = note.createdDate.toString()
        labelDate.text = answer

        val button = viewHolder.editButton
        button.text = if (noteMockup.isDeleted) "(Deleted)" else "Edit >"
        button.isEnabled = !noteMockup.isDeleted
    }
}*/
