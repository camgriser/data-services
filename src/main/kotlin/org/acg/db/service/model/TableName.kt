package org.acg.db.service.model

import java.io.Serializable

data class TableName (
    val name: String,
    val schema: String
): Serializable {

    constructor(name: String) : this(name, "")

    constructor(): this("", "")

    override fun toString(): String {
        return if (schema.isNotEmpty()) "$schema.$name" else name
    }
}