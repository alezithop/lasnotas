package com.sacg.lasnotas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import models.Note
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class NewNoteActivity: AppCompatActivity() {

    internal lateinit var titleET: EditText
    internal lateinit var contentET: EditText
    internal lateinit var imageHeaderET: EditText
    internal lateinit var imageBtn: ImageButton
    internal lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_note_screen)

        titleET = findViewById(R.id.etTitle)
        contentET = findViewById(R.id.etContent)
        imageHeaderET = findViewById(R.id.etImageHeader)
        imageBtn = findViewById(R.id.btnImage)
        saveBtn = findViewById(R.id.btnSave)

        saveBtn.setOnClickListener() {
            val calendar = Calendar.getInstance()

            var tempTitleStr = titleET.text.toString()
            var tempContentStr = contentET.text.toString()
            var tempImageHeaderStr = imageHeaderET.text.toString()

            val tempNewNote = Note("$tempTitleStr | $tempContentStr | $tempImageHeaderStr", calendar.time,false)

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("newNoteAdded", tempNewNote)
            }
            startActivity(intent)
        }
    }

    private fun createNewNote(_note: Note): ArrayList<Note>  {
        return Note.addNote(_note)
    }

}