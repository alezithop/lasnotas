package models

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class NoteEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "tbl_notes"
            val COLUMN_ID_NOTE = "id_note"
            val COLUMN_TITLE = "title"
            val COLUMN_CONTENT = "content"
            val COLUMN_CREATED_DATE = "created_date"
            val COLUMN_IS_DELETED = "is_deleted"
        }
    }

    class ImageEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "tbl_images"
            val COLUMN_ID_IMAGE = "id_image"
            val COLUMN_TITLE = "image_title"
            val COLUMN_URI = "uri"
            val COLUMN_ID_NOTE = "id_note"
            val COLUMN_CREATED_DATE = "created_date"
            val COLUMN_IS_DELETED = "is_deleted"
        }
    }
}