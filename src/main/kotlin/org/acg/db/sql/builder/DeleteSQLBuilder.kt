package org.acg.db.sql.builder

import org.acg.db.service.model.TableName

class DeleteSQLBuilder {

    companion object {
        fun generateDeleteSQL(tableName: TableName): String {
            var sql = ""
            return "DELETE FROM ${tableName.toString()}"
        }
    }
}