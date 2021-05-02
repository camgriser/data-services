package org.acg.db.sql.builder

import org.acg.db.service.model.SetColumn
import org.acg.db.service.model.TableName

class UpdateSQLBuilder {

    companion object {
        fun generateUpdateSQL(name: TableName, setColumns: Set<SetColumn>): String {
            var sql = ""
            if (setColumns.isNotEmpty()) {
                sql = "UPDATE $name \nSET "
                sql = sql.plus(generateSetSQL(setColumns))
            }
            return sql
        }

        private fun generateSetSQL(setColumns: Set<SetColumn>): String {
            return setColumns.joinToString {
                it.generateSetSQL()
            }
        }
    }
}