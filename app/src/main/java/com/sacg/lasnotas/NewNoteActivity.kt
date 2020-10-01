package com.sacg.lasnotas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import models.NoteMockup
import models.NoteModel
import java.util.*
import kotlin.collections.ArrayList

class NewNoteActivity: AppCompatActivity() {

    internal lateinit var titleET: EditText
    internal lateinit var contentET: EditText
    internal lateinit var imageHeaderET: EditText
    internal lateinit var imageBtn: ImageButton
    internal lateinit var saveBtn: Button
    internal lateinit var userImageIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_note_screen)

        titleET = findViewById(R.id.etTitle)
        contentET = findViewById(R.id.etContent)
        imageHeaderET = findViewById(R.id.etImageHeader)
        imageBtn = findViewById(R.id.btnImage)
        userImageIV = findViewById(R.id.ivImage)
        saveBtn = findViewById(R.id.btnSave)

        imageBtn.setOnClickListener() {
            userImageIV.visibility = View.VISIBLE
        }

        saveBtn.setOnClickListener() {
            val calendar = Calendar.getInstance()

            var tempTitleStr = titleET.text.toString()
            var tempContentStr = contentET.text.toString()
            var tempImageHeaderStr = imageHeaderET.text.toString()

            //val tempNewNote = NoteMockup("$tempTitleStr | $tempContentStr | $tempImageHeaderStr", calendar.time,false)

            val intent = Intent(this, MainActivity::class.java).apply {
                // putExtra("newNoteAdded", tempNewNote)
                putExtra("newTempTitle", tempTitleStr)
                putExtra("newTempContent", tempContentStr)
            }
            startActivity(intent)
        }
    }

    private fun createNewNote(_noteMockup: NoteMockup): ArrayList<NoteMockup>  {
        return NoteMockup.addNote(_noteMockup)
    }

}