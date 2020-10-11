package models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotesDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_NOTE_ENTRIES)
        db.execSQL(SQL_CREATE_IMAGE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_NOTE_ENTRIES)
        db.execSQL(SQL_DELETE_IMAGE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDowngrade(db, oldVersion, newVersion)
    }
    // ====================================================  NOTES SECTION BEGIN  ====================================================
    @Throws(SQLiteConstraintException::class)
    fun insertNote(note: NoteModel): Long {
        // ========================================================================
        // Get Date for today to act as Time Stamp
        val calendar = Calendar.getInstance()
        //rollback 90 days
        calendar.add(Calendar.DAY_OF_YEAR, 0)
        //now the date is 90 days back
        Log.i("MyApp", "0 days ago: (texto) " + calendar.time.toString())
        Log.i("MyApp", "0 days ago:" + calendar.time)

        var date = calendar.time
        val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
        val answer: String = formatter.format(date)
        Log.d("answer", answer)
        // ========================================================================

        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.NoteEntry.COLUMN_TITLE, note.title)
        values.put(DBContract.NoteEntry.COLUMN_CONTENT, note.content)
        values.put(DBContract.NoteEntry.COLUMN_CREATED_DATE, answer)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.NoteEntry.TABLE_NAME, null, values)

        return newRowId
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteNote(noteID: Int): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' section of query
        val filter = DBContract.NoteEntry.COLUMN_ID_NOTE + " LIKE ?"
        // Specify arguments in placeholder order
        val filterArgs = arrayOf(noteID.toString())
        // Issue SQL statement
        db.delete(DBContract.NoteEntry.TABLE_NAME, filter, filterArgs)

        return true
    }

    fun readNote(noteID: Int): ArrayList<NoteModel> {
        val notes = ArrayList<NoteModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM " + DBContract.NoteEntry.TABLE_NAME + " WHERE "
                    + DBContract.NoteEntry.COLUMN_ID_NOTE + "='" + noteID + "'", null)
        } catch (e: SQLiteException) {
            // id table not yet present, create it
            db.execSQL(SQL_CREATE_NOTE_ENTRIES)
            return ArrayList()
        }

        var title: String
        var content: String
        var createdDate: String
        var isDeleted: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                title = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_TITLE))
                content = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_CONTENT))
                createdDate = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_CREATED_DATE))
                isDeleted = cursor.getInt(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_IS_DELETED))

                notes.add(NoteModel(noteID, title, content, createdDate, isDeleted))
                cursor.moveToNext()
            }
        }
        return notes
    }

    fun readAllNotes(): ArrayList<NoteModel> {
        val notes = ArrayList<NoteModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM " + DBContract.NoteEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_NOTE_ENTRIES)
            return ArrayList()
        }

        var noteID: Int
        var title: String
        var content: String
        var createdDate: String
        var isDeleted: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                noteID = cursor.getInt(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_ID_NOTE))
                title = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_TITLE))
                content = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_CONTENT))
                createdDate = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_CREATED_DATE))
                isDeleted = cursor.getInt(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_IS_DELETED))

                notes.add(NoteModel(noteID, title, content, createdDate, isDeleted))
                cursor.moveToNext()
            }
        }
        return notes
    }
    // ====================================================  NOTES SECTION ENDS  ====================================================

    // ====================================================  IMAGES SECTION BEGIN  ====================================================
    @Throws(SQLiteConstraintException::class)
    fun insertImage(image: ImageModel): Long {
        // ========================================================================
        // Get Date for today to act as Time Stamp
        val calendar = Calendar.getInstance()
        //rollback 90 days
        calendar.add(Calendar.DAY_OF_YEAR, 0)
        //now the date is 90 days back
        Log.i("MyApp", "0 days ago: (texto) " + calendar.time.toString())
        Log.i("MyApp", "0 days ago:" + calendar.time)

        var date = calendar.time
        val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
        val answer: String = formatter.format(date)
        Log.d("answer", answer)
        // ========================================================================

        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.ImageEntry.COLUMN_TITLE, image.title)
        values.put(DBContract.ImageEntry.COLUMN_URI, image.uri)
        values.put(DBContract.ImageEntry.COLUMN_ID_NOTE, image.id_note)
        values.put(DBContract.ImageEntry.COLUMN_CREATED_DATE, answer)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.ImageEntry.TABLE_NAME, null, values)

        return newRowId
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteImage(imageID: Int): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' section of query
        val filter = DBContract.ImageEntry.COLUMN_ID_IMAGE + " LIKE ?"
        // Specify arguments in placeholder order
        val filterArgs = arrayOf(imageID.toString())
        // Issue SQL statement
        db.delete(DBContract.ImageEntry.TABLE_NAME, filter, filterArgs)

        return true
    }

    fun readImage(imageID: Int): ArrayList<ImageModel> {
        val images = ArrayList<ImageModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM " + DBContract.ImageEntry.TABLE_NAME + " WHERE "
                    + DBContract.ImageEntry.COLUMN_ID_IMAGE + "='" + imageID + "'", null)
        } catch (e: SQLiteException) {
            // id table not yet present, create it
            db.execSQL(SQL_CREATE_IMAGE_ENTRIES)
            return ArrayList()
        }

        var title: String
        var uri: String
        var idNote: Int
        var createdDate: String
        var isDeleted: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                title = cursor.getString(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_TITLE))
                uri = cursor.getString(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_URI))
                idNote = cursor.getInt(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_ID_NOTE))
                createdDate = cursor.getString(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_CREATED_DATE))
                isDeleted = cursor.getInt(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_IS_DELETED))

                images.add(ImageModel(imageID, title, uri, idNote, createdDate, isDeleted))
                cursor.moveToNext()
            }
        }
        return images
    }

    fun readAllImages(): ArrayList<ImageModel> {
        val images = ArrayList<ImageModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM " + DBContract.ImageEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_IMAGE_ENTRIES)
            return ArrayList()
        }

        var idImage: Int
        var title: String
        var uri: String
        var idNote: Int
        var createdDate: String
        var isDeleted: Int

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                idImage = cursor.getInt(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_ID_IMAGE))
                title = cursor.getString(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_TITLE))
                uri = cursor.getString(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_URI))
                idNote = cursor.getInt(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_ID_NOTE))
                createdDate = cursor.getString(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_CREATED_DATE))
                isDeleted = cursor.getInt(cursor.getColumnIndex(DBContract.ImageEntry.COLUMN_IS_DELETED))

                images.add(ImageModel(idImage, title, uri, idNote, createdDate, isDeleted))
                cursor.moveToNext()
            }
        }
        return images
    }
    // ====================================================  IMAGES SECTION ENDS  ====================================================

    companion object {
        // If you change the database schema, you must increment the database version
        val DATABASE_VERSION = 3
        val DATABASE_NAME = "LasNotas.db"

        private val SQL_CREATE_NOTE_ENTRIES = "CREATE TABLE " + DBContract.NoteEntry.TABLE_NAME +
                " (" + DBContract.NoteEntry.COLUMN_ID_NOTE + " INTEGER PRIMARY KEY," +
                " " + DBContract.NoteEntry.COLUMN_TITLE + " TEXT," +
                " " + DBContract.NoteEntry.COLUMN_CONTENT + " TEXT," +
                " " + DBContract.NoteEntry.COLUMN_CREATED_DATE + " TEXT," +
                " " + DBContract.NoteEntry.COLUMN_IS_DELETED + " INTEGER)"

        private val SQL_DELETE_NOTE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.NoteEntry.TABLE_NAME

        private val SQL_CREATE_IMAGE_ENTRIES = "CREATE TABLE " + DBContract.ImageEntry.TABLE_NAME +
                " (" + DBContract.ImageEntry.COLUMN_ID_IMAGE + " INTEGER PRIMARY KEY," +
                " " + DBContract.ImageEntry.COLUMN_TITLE + " TEXT," +
                " " + DBContract.ImageEntry.COLUMN_URI + " TEXT," +
                " " + DBContract.ImageEntry.COLUMN_ID_NOTE + " INTEGER," +
                " " + DBContract.ImageEntry.COLUMN_CREATED_DATE + " TEXT," +
                " " + DBContract.ImageEntry.COLUMN_IS_DELETED + " INTEGER," +
                " FOREIGN KEY(" + DBContract.ImageEntry.COLUMN_ID_NOTE + ") REFERENCES " + DBContract.NoteEntry.TABLE_NAME + "(" + DBContract.NoteEntry.COLUMN_ID_NOTE + "))"

        private val SQL_DELETE_IMAGE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.ImageEntry.TABLE_NAME
    }
}