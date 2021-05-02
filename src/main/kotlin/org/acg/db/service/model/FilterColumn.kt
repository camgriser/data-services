package org.acg.db.service.model

class FilterColumn {

    private var name: String = ""
    private var comparator: Comparator
    private var schema: String = ""

    constructor(name: String, schema: String, comparator: Comparator) {
        this.name = name
        this.schema = schema
        this.comparator = comparator
    }

    fun filterName(): String {
        return if (schema.isNotEmpty()) "$schema.$name" else name
    }

    fun filterParam(): String {
        return when (comparator) {
            Comparator.IN -> "(:${paramName()})"
            else -> ":${paramName()}"
        }
    }

    fun generateFilterClause(): String {
        return "${filterName()} ${comparator.value} ${filterParam()}"
    }

    fun paramName(): String {
        return if (schema.isNotEmpty()) "${schema.toLowerCase()} ${name.toLowerCase().capitalize()}" else name
    }
}