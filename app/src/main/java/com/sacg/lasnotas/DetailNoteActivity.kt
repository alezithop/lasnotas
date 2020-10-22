package com.sacg.lasnotas

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import models.NotesDBHelper

class DetailNoteActivity: AppCompatActivity()  {

    internal lateinit var titleTV: TextView
    internal lateinit var contentTV: TextView
    internal lateinit var imageHeaderTV: TextView
    internal lateinit var imageBtn: ImageButton
    internal lateinit var saveBtn: Button
    internal lateinit var userImageIV: ImageView
    internal lateinit var uriTV: TextView
    internal lateinit var tempUri: String

    lateinit var notesDBHelper: NotesDBHelper

    val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_note_details)

        notesDBHelper = NotesDBHelper(this)

        titleTV = findViewById(R.id.etTitle)
        contentTV = findViewById(R.id.etContent)
        imageHeaderTV = findViewById(R.id.etImageHeader)
        imageBtn = findViewById(R.id.btnImage)
        userImageIV = findViewById(R.id.ivImage)
        saveBtn = findViewById(R.id.btnSave)
        uriTV = findViewById(R.id.tvUri)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                val noteID = intent.getSerializableExtra("noteID")
                titleTV.setText(noteID.toString())
            }
        }
    }

}