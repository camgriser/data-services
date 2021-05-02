package org.acg.db.sql.builder

import org.acg.db.service.model.SortColumn

class SortingSQLBuilder {

    companion object {
        fun generateSortingSQL(sortParams: List<SortColumn>): String {
            var sql = ""
            if (sortParams.isNotEmpty()) {
                val sortSql = ArrayList<String>()
                sortParams.forEach {
                    sortSql.add(it.generateSortClause())
                }
                sql = "ORDER BY${sortSql.joinToString(",")}\n"
            }
            return sql
        }
    }
}