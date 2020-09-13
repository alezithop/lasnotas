package com.sacg.lasnotas

import adapters.ForecastListAdapter
import adapters.NotesAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.Note

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {

    lateinit var notes: ArrayList<Note>

    internal val TAG = MainActivity::class.java.simpleName
    internal lateinit var spinner: Spinner
    internal lateinit var rvNotes: RecyclerView
    internal lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spn_order)

        ArrayAdapter.createFromResource(this, R.array.array_order, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        spinner.onItemSelectedListener = this

        rvNotes = findViewById<View>(R.id.rvNotes) as RecyclerView
        notes = Note.createNotesList(20)
        adapter = NotesAdapter(notes)
        //rvNotes.adapter = adapter
        //rvNotes.layoutManager = LinearLayoutManager(this)
        /*val forecastList = findViewById<RecyclerView>(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)
        forecastList.adapter = ForecastListAdapter(items)*/
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        Log.d(TAG, "clicked the element: " + parent.getItemAtPosition(pos))
        sortNotes(parent.getItemAtPosition(pos) as String)
    }

    private fun sortNotes(type: String) {
        if (type == "Newest first") {
            adapter = NotesAdapter(notes.sortedWith(compareByDescending({ it.createdDate })))
            rvNotes.adapter = adapter
            rvNotes.layoutManager = LinearLayoutManager(this)
        }
        if (type == "Oldest first") {
            adapter = NotesAdapter(notes.sortedWith(compareBy({ it.createdDate })))
            rvNotes.adapter = adapter
            rvNotes.layoutManager = LinearLayoutManager(this)
        }
        if (type == "Title") {
            adapter = NotesAdapter(notes.sortedWith(compareBy({ it.noteContent })))
            rvNotes.adapter = adapter
            rvNotes.layoutManager = LinearLayoutManager(this)
        }
    }
}
