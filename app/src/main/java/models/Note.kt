package models

class Note(val noteContent: String, val isDeleted: Boolean) {
    companion object{
        private var lastContactId = 0
        fun createNotesList(numnotes: Int) : ArrayList<Note> {
            val notes = ArrayList<Note>()
            for (i in 1..numnotes) {
                notes.add(Note("Person " + ++lastContactId, i <= numnotes / 2))
            }
            return notes
        }
    }
}