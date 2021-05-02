package org.acg.db.service.model

import java.io.Serializable

data class SelectTable (
    val columns: List<String>,
    val tableName: TableName
): Serializable {
    constructor() : this(
        ArrayList<String>(), TableName()
    )

    constructor(tableName: TableName): this(
        ArrayList<String>(), TableName("")
    )
}