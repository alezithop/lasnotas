package com.sacg.lasnotas

import adapters.CellClickListener
import adapters.NotesAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.ImageModel
import models.NoteModel
import models.NotesDBHelper

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, CellClickListener {

    // lateinit var noteMockups: ArrayList<_NoteMockup>

    internal val TAG = MainActivity::class.java.simpleName
    internal lateinit var spinner: Spinner
    internal lateinit var rvNotes: RecyclerView
    internal lateinit var adapter: NotesAdapter
    internal lateinit var addNoteBtn: Button

    lateinit var notesDBHelper: NotesDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesDBHelper = NotesDBHelper(this)

        spinner = findViewById(R.id.spn_order)
        addNoteBtn = findViewById(R.id.btn_new_note)

        ArrayAdapter.createFromResource(this, R.array.array_order, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        spinner.onItemSelectedListener = this

        rvNotes = findViewById<View>(R.id.rvNotes) as RecyclerView

        // Initialize the array template of Notes
        // noteMockups = _NoteMockup.createNotesList(20)
        adapter = NotesAdapter(getAllNotes(), this)
        //rvNotes.adapter = adapter
        //rvNotes.layoutManager = LinearLayoutManager(this)
        /*val forecastList = findViewById<RecyclerView>(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)
        forecastList.adapter = ForecastListAdapter(items)*/

        /*val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                val newTempTitle = intent.getSerializableExtra("newTempTitle")
                val newTempContent = intent.getSerializableExtra("newTempContent")
                val newTempUri = intent.getSerializableExtra("newTempUri")
                // val newNoteAdded = intent.getSerializableExtra("newNoteAdded") as _NoteMockup
                if(newTempTitle != null) {
                    Log.d("HELLO", "Content data received is $newTempTitle WITH $newTempContent .")

                    // noteMockups.add(newNoteAdded)
                    val newTempNote = NoteModel(0, newTempTitle.toString(), newTempContent.toString(), "", 0)
                    val newTempImage = ImageModel(0, "title", newTempUri.toString(),0, "", 0)
                    // addNote(newTempNote)
                }
            }
        }*/

        addNoteBtn.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCellClickListener(note: NoteModel) {
        //Toast.makeText(this, "Cell clicked", Toast.LENGTH_LONG).show()
        val intent = Intent(this, DetailNoteActivity::class.java)
            .apply {
                putExtra("noteID", note.id_note)
            }
        startActivity(intent)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        Log.d(TAG, "clicked the element: " + parent.getItemAtPosition(pos))
        sortNotes(parent.getItemAtPosition(pos) as String)
    }

    private fun getAllNotes() : ArrayList<NoteModel> {
        return notesDBHelper.readAllNotes()
    }

    private fun sortNotes(type: String) {
        if (type == "Newest first") {
            // pass the data to the NotesAdapter class to generate the new list updated based in the user selection, showing "Newest items first"
            // adapter = NotesAdapter(noteMockups.sortedWith(compareByDescending({ it.createdDate })))

            adapter = NotesAdapter(getAllNotes().sortedWith(compareByDescending({ it.createdDate })), this)
            // update the recyclerview from activity_main screen to display the updated and sorted items
            rvNotes.adapter = adapter
            rvNotes.layoutManager = LinearLayoutManager(this)
        }
        if (type == "Oldest first") {
            // pass the data to the NotesAdapter class to generate the new list updated based in the user selection, showing "Oldest items first"
            // adapter = NotesAdapter(noteMockups.sortedWith(compareBy({ it.createdDate })))
            adapter = NotesAdapter(getAllNotes().sortedWith(compareBy({ it.createdDate })), this)
            // update the recyclerview from activity_main screen to display the updated and sorted items
            rvNotes.adapter = adapter
            rvNotes.layoutManager = LinearLayoutManager(this)
        }
        if (type == "Title") {
            // pass the data to the NotesAdapter class to generate the new list updated based in the user selection, showing items sorted by "Title"
            //adapter = NotesAdapter(noteMockups.sortedWith(compareBy({ it.noteContent })))
            adapter = NotesAdapter(getAllNotes().sortedWith(compareBy({ it.title })), this)
            // update the recyclerview from activity_main screen to display the updated and sorted items
            rvNotes.adapter = adapter
            rvNotes.layoutManager = LinearLayoutManager(this)
        }
    }
}
