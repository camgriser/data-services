package org.acg.db.sql.builder

import org.acg.db.service.model.FilterColumn

class FilterSQLBuilder {

    companion object {
        fun generateFilterSQL(filterColumns: Set<FilterColumn>, where: Boolean): String {
            val columnClauses = ArrayList<String>()
            var sql = ""
            if (filterColumns.isNotEmpty()) {
                filterColumns.forEach { filterPair ->
                    columnClauses.add(filterPair.generateFilterClause())
                }
                val finalClause = columnClauses.joinToString("and")
                sql = if (where) " WHERE${finalClause}" else finalClause
            }
            return sql
        }

        fun convertFilterMap(filterMap: Map<FilterColumn, Any?>): Map<String, Any?> {
            val paramMap = HashMap<String, Any?>()
            filterMap.keys.forEach {
                paramMap[it.paramName()] = filterMap[it]
            }
            return paramMap
        }
    }
}