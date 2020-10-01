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
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_NOTE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDowngrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertNote(note: NoteModel): Boolean {
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

        return true
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

    companion object {
        // If you change the database schema, you must increment the database version
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "LasNotas.db"

        private val SQL_CREATE_NOTE_ENTRIES = "CREATE TABLE " + DBContract.NoteEntry.TABLE_NAME +
                " (" + DBContract.NoteEntry.COLUMN_ID_NOTE + " INTEGER PRIMARY KEY," +
                " " + DBContract.NoteEntry.COLUMN_TITLE + " TEXT," +
                " " + DBContract.NoteEntry.COLUMN_CONTENT + " TEXT," +
                " " + DBContract.NoteEntry.COLUMN_CREATED_DATE + " TEXT," +
                " " + DBContract.NoteEntry.COLUMN_IS_DELETED + " INTEGER)"

        private val SQL_DELETE_NOTE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.NoteEntry.TABLE_NAME
    }
}