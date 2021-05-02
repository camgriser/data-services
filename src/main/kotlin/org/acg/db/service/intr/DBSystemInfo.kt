package org.acg.db.service.intr

import org.acg.db.service.model.CreateColumn
import org.acg.db.service.model.TableName

interface DBSystemInfo {

    fun getColumns(schema: String, tableName: TableName): List<CreateColumn>
    fun getTables(schema: String, search: String, owner: String): List<TableName>
    fun getSchemas(owner: String): List<String>
}