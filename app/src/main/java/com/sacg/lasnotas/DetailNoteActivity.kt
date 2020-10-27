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
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.new_note_screen.*
import models.ImageModel
import models.NoteModel
import models.NotesDBHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class DetailNoteActivity: AppCompatActivity()  {

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

    var globalNoteID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_note_details)

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

        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                val noteID = intent.getSerializableExtra("noteID")
                globalNoteID = noteID.toString().toInt()
                val note = getNote(noteID.toString().toInt()).first()
                val image = getImage(noteID.toString().toInt()).first()
                var header = "${getString(R.string.detail_note_title)} ( ${note.id_note.toString()} )"
                tvHeader.text = header
                titleET.setText(note.title)
                contentET.setText(note.content)
                imageHeaderET.setText(image.title)
                userImageIV.visibility = View.VISIBLE
                userImageIV.setImageURI(Uri.parse(image.uri))
                // uriTV.setText(image.uri)
                tempUri = image.uri
            }
        }

        saveBtn.setOnClickListener() {
            val calendar = Calendar.getInstance()

            var tempTitleStr = titleET.text.toString()
            var tempContentStr = contentET.text.toString()
            var temImageTitleStr = imageHeaderET.text.toString()
            var tempImageHeaderStr = imageHeaderET.text.toString()

            val tempIdNote = updateNote(NoteModel(globalNoteID, tempTitleStr, tempContentStr, calendar.time.toString(), 0))

            if (tempIdNote <= 0) {
                Log.d("ISSUE", "There was an issue updating the note. Note was not updated.")
                val toast = Toast.makeText(applicationContext, "There was an issue updating the note. Note was not updated", Toast.LENGTH_LONG).show()
            } else {
                if(tempImageHeaderStr.isNotEmpty() || tempUri.toString().isNotEmpty()) {
                    val tempIdImage = updateImage(ImageModel(0, temImageTitleStr, tempUri, globalNoteID, calendar.time.toString(), 0))
                    if(tempIdImage < 0) {
                        Log.d("ISSUE", "There was an issue saving the image. Image was not updated.")
                        val toast = Toast.makeText(applicationContext, "There was an issue updating the image. Image was not updated.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getNote(noteID: Int) : ArrayList<NoteModel> {
        var result = notesDBHelper.readNote(noteID)
        return result
    }

    private fun getImage(noteID: Int) : ArrayList<ImageModel> {
        var result = notesDBHelper.readImage(noteID)
        return result
    }

    private fun updateNote(note: NoteModel) : Int {
        var result = notesDBHelper.updateNote(note)
        return result
    }

    private fun updateImage(image: ImageModel) : Long {
        var result = notesDBHelper.reeplaceImage(image)
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
            // uriTV.text = tempUri
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
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath).toString()
    }

}