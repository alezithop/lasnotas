package models

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Note(val noteContent: String, val createdDate: String, val isDeleted: Boolean) {
    companion object{
        private var lastContactId = 0
        fun createNotesList(numnotes: Int) : ArrayList<Note> {
            val notes = ArrayList<Note>()
            for (i in 1..numnotes) {
                var date = Date()
                val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
                val answer: String = formatter.format(date)
                Log.d("answer",answer)
                //Calendar set to the current date

                //Calendar set to the current date
                val calendar = Calendar.getInstance()
                //rollback 90 days
                calendar.add(Calendar.DAY_OF_YEAR, -(0..20).random())
                //now the date is 90 days back
                Log.i("MyApp", "90 days ago:" + calendar.time.toString())

                notes.add(Note("Person " + ++lastContactId, calendar.time.toString(), i <= numnotes / 2))
            }
            return notes
        }
    }
}