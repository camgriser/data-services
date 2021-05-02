package org.acg.db.sql.builder

import org.acg.db.service.model.*

class SQLBuilder (sql: String) {

    constructor() : this("")

    val stringBuilder = StringBuilder(sql)

    fun generateFilterSQL(filterPairs: Set<FilterColumn>, where: Boolean): SQLBuilder {
        stringBuilder.append(FilterSQLBuilder.generateFilterSQL(filterPairs, where))
        return this
    }

    fun generateInsertSQL(name: String, rows: List<Map<String, Any?>>): Pair<SQLBuilder, Map<String, Any?>> {
        val pair = InsertSQLBuilder.generateInsertSQL(name, rows)
        stringBuilder.append(pair.first)
        return Pair(this, pair.second)
    }

    fun generateSortingSQL(sortParams: List<SortColumn>): SQLBuilder {
        stringBuilder.append(SortingSQLBuilder.generateSortingSQL(sortParams))
        return this
    }

    fun generateUpdateSQL(name: TableName, setColumns: Set<SetColumn>): SQLBuilder {
        stringBuilder.append(UpdateSQLBuilder.generateUpdateSQL(name, setColumns))
        return this
    }

    fun generateSelectSQL(table: SelectTable): SQLBuilder {
        stringBuilder.append(SelectSQLBuilder.generateSelectSQL(table))
        return this
    }

    fun generateDeleteSQL(tableName: TableName): SQLBuilder {
        stringBuilder.append(DeleteSQLBuilder.generateDeleteSQL(tableName))
        return this
    }

    fun build(): String {
        return stringBuilder.toString()
    }

    fun generateLimitSQL(pageSize: Int): SQLBuilder {
        stringBuilder.append("LIMIT $pageSize")
        return this
    }

    companion object {
        fun convertFilterMap(filterMap: Map<FilterColumn, Any?>): Map<String, Any?> =
            FilterSQLBuilder.convertFilterMap(filterMap)
    }
}