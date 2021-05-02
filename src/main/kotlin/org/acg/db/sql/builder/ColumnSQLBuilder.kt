package org.acg.db.sql.builder

class ColumnSQLBuilder {

    companion object {
        fun generateColumnSQL(columns: List<String>): String {
            return columns.joinToString(", ")
        }
    }
}