package org.acg.db.service.model

class SortColumn {

    private var name: String
    private var schema: String = ""
    private var sortBy: SortBy

    constructor(name: String, schema: String, sortBy: SortBy) {
        this.name = name
        this.schema = schema
        this.sortBy = sortBy
    }

    constructor(name: String, sortBy: SortBy) {
        this.name = name
        this.sortBy = sortBy
    }

    fun sortName(): String = if (schema.isNotEmpty()) "$schema.$name" else name

    fun generateSortClause(): String = " ${sortName()} ${sortBy.value}"
}