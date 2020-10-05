package models

import java.util.*

class _Note {
    var id_note: Int = 0;
    var title: String = "";
    var content: String = "";
    var createdDate: String = "";
    var isDeleted: Int = 0;

    constructor(title: String, content: String) {
        this.title = title;
        this.content = content;
    }

}