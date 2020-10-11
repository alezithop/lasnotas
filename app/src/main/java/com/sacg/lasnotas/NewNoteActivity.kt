package com.sacg.lasnotas

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import models.ImageModel
import models.NoteModel
import models.NotesDBHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class NewNoteActivity: AppCompatActivity() {

    internal lateinit var titleET: EditText
    internal lateinit var contentET: EditText
    internal lateinit var imageHeaderET: EditText
    internal lateinit var imageBtn: ImageButton
    internal lateinit var saveBtn: Button
    internal lateinit var userImageIV: ImageView
    internal lateinit var uriTV: TextView
    internal lateinit var tempUri: String

    lateinit var notesDBHelper: NotesDBHelper


    val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_note_screen)

        notesDBHelper = NotesDBHelper(this)

        titleET = findViewById(R.id.etTitle)
        contentET = findViewById(R.id.etContent)
        imageHeaderET = findViewById(R.id.etImageHeader)
        imageBtn = findViewById(R.id.btnImage)
        userImageIV = findViewById(R.id.ivImage)
        saveBtn = findViewById(R.id.btnSave)
        uriTV = findViewById(R.id.tvUri)

        imageBtn.setOnClickListener() {
            openGalleryForImage()
        }

        saveBtn.setOnClickListener() {
            val calendar = Calendar.getInstance()

            var tempTitleStr = titleET.text.toString()
            var tempContentStr = contentET.text.toString()
            var temImageTitleStr = imageHeaderET.text.toString()
            var tempImageHeaderStr = imageHeaderET.text.toString()
            // tempUri = saveImageToInternalStorage()

            // noteMockups.add(newNoteAdded)
            // val newTempNote = NoteModel(0, newTempTitle.toString(), newTempContent.toString(), "", 0)
            val tempIdNote = addNote(NoteModel(0, tempTitleStr, tempContentStr, calendar.time.toString(), 0))

            if (tempIdNote < 0) {
                Log.d("ISSUE", "There was an issue saving the note.")
                val toast = Toast.makeText(applicationContext, "There was an issue saving the note.", Toast.LENGTH_LONG).show()
            } else {
                if(tempImageHeaderStr.isNotEmpty() || tempUri.toString().isNotEmpty()) {
                    val tempIdImage = addImage(ImageModel(0, temImageTitleStr, tempUri,tempIdNote.toInt(), calendar.time.toString(), 0))
                    // val tempIdImage = addImage(ImageModel(0, "title", tempUri.toString(),tempIdNote.toInt(), calendar.time.toString(), 0))
                    if(tempIdImage < 0) {
                        Log.d("ISSUE", "There was an issue saving the image.")
                        val toast = Toast.makeText(applicationContext, "There was an issue saving the image.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            //val tempNewNote = _NoteMockup("$tempTitleStr | $tempContentStr | $tempImageHeaderStr", calendar.time,false)

            val intent = Intent(this, MainActivity::class.java)
                /*.apply {
                // putExtra("newNoteAdded", tempNewNote)
                putExtra("newTempTitle", tempTitleStr)
                putExtra("newTempContent", tempContentStr)
                putExtra("newTempUri", tempUri)
            }*/
            startActivity(intent)
        }
    }

    private fun addNote(note: NoteModel) : Long {
        var result = notesDBHelper.insertNote(note)
        return result
    }

    private fun addImage(image: ImageModel) : Long {
        var result = notesDBHelper.insertImage(image)
        return result
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            userImageIV.visibility = View.VISIBLE
            // Handle chosen image
            userImageIV.setImageURI(data?.data)
            tempUri = saveImageToInternalStorage()
            uriTV.text = tempUri
        }
    }

    private fun saveImageToInternalStorage() : String {
        var bitmap = (userImageIV.drawable as BitmapDrawable).bitmap
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
            // uriTV.text = Uri.parse(file.absolutePath).toString()
        } catch (e:IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath).toString()
    }

/*
    private fun createNewNote(_noteMockup: _NoteMockup): ArrayList<_NoteMockup>  {
        return _NoteMockup.addNote(_noteMockup)
    }
*/
}