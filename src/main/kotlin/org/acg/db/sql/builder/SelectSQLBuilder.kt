package org.acg.db.sql.builder

import org.acg.db.service.model.SelectTable

class SelectSQLBuilder {

    companion object {
        fun generateSelectSQL(table: SelectTable): String {
            var sql = ""
            if (table.columns.isNotEmpty()) {
                sql = "SELECT ${ColumnSQLBuilder.generateColumnSQL(table.columns)}\nFROM ${table.tableName}\n"
            }
            return sql
        }
    }
}