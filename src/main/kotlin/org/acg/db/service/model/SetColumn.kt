package org.acg.db.service.model

class SetColumn {

    var name: String
    var setColumnName: String = ""
    var paramValue: Any? = null

    constructor(name: String, paramValue: Any?) {
        this.name = name
        this.paramValue = paramValue
    }

    constructor(name: String, setColumnName: String) {
        this.name = name
        this.setColumnName = setColumnName
    }

    fun generateSetSQL(): String = if (setColumnName.isNotEmpty()) "$name = $setColumnName" else "$name = :$name"
}