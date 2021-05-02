package org.acg.db.sql.builder

import org.acg.db.service.model.CreateColumn
import org.acg.db.service.model.CreateTable

class CreateTableSQLBuilder {

    companion object {
        fun generateCreateTableSQL(createTable: CreateTable): String {
            var sql = ""
            if (createTable.tableName.toString().isNotEmpty()) {
                sql = "CREATE TABLE ${createTable.tableName} (\n"
                createTable.columns.forEachIndexed { i, it ->
                    sql += generateColumn(it)
                    sql += if (i != createTable.columns.size - 1) ",\n" else "\n"
                }
                sql += ");"
            }
            return sql
        }

        private fun generateColumn(createColumn: CreateColumn) =
            "\t${createColumn.name}\t${createColumn.type.displayName}"
    }


}