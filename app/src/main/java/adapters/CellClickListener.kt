package adapters

import models.NoteModel

interface CellClickListener {
    fun onCellClickListener(note: NoteModel)
}