package org.acg.db.service.intr

import org.acg.db.service.model.FilterColumn
import org.acg.db.service.model.SetColumn
import org.acg.db.service.model.TableName

interface DBUpdateRepo {

    fun update(sql: String, filterMap: Map<String, Any?>): Int
    fun update(sql: String, filterMap: Map<FilterColumn, Any?>, where: Boolean): Int
    fun update(table: TableName, setColumns: Set<SetColumn>, filterMap: Map<FilterColumn, Any?>): Int
    fun bulkUpdate(tableName: TableName, setColumns: Set<SetColumn>, pks: List<Map<String, Any?>>)
}