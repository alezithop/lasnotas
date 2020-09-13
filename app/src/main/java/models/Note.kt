package models

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Note(val noteContent: String, val createdDate: Date, val isDeleted: Boolean) {
    companion object{
        private var lastContactId = 0
        fun createNotesList(numnotes: Int) : ArrayList<Note> {
            val notes = ArrayList<Note>()
            for (i in 1..numnotes) {
                /*var date = Date()
                val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
                val answer: String = formatter.format(date)
                Log.d("answer",answer)*/
                //Calendar set to the current date

                //Calendar set to the current date
                val calendar = Calendar.getInstance()
                //rollback 90 days
                calendar.add(Calendar.DAY_OF_YEAR, -(0..90).random())
                //now the date is 90 days back
                Log.i("MyApp", "90 days ago: (texto) " + calendar.time.toString())
                Log.i("MyApp", "90 days ago:" + calendar.time)

                val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                val outputStrLength = (1..10).random()
                // Random().ints(10,0,source.length)
                val randomNoteContent = Random().ints(outputStrLength.toLong(), 0, source.length)
                    .toArray()
                    .map(source::get)
                    .joinToString("")
                // notes.add(Note("$randomNoteContent ($lastContactId)", calendar.time, i <= numnotes / 2))
                notes.add(Note(randomNoteContent + " (" + ++lastContactId  + ")", calendar.time, i <= numnotes / 2))
            }
            return notes
        }
    }
}