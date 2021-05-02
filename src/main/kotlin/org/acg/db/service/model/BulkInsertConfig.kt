package org.acg.db.service.model

import java.io.Serializable

class BulkInsertConfig: Serializable {

    val table: SelectTable
    val delim: String
    val nullValue: String
    val filePath: String

    constructor() {
        this.table = SelectTable()
        this.delim = "|"
        this.nullValue = "NULL"
        this.filePath = ""
    }

    constructor(table: SelectTable, delim: String, nullValue: String, filePath: String) {
        this.table = table
        this.delim = if (delim.isNotEmpty()) delim else "|"
        this.nullValue = if (nullValue.isNotEmpty()) nullValue else "NULL"
        this.filePath = filePath
    }
}