package org.acg.db.service.intr

import org.acg.db.service.model.FilterColumn
import org.acg.db.service.model.TableName

interface DBDeleteRepo {

    fun delete(sql: String, filterMap: Map<String, Any?>): Int
    fun delete(sql: String, filterMap: Map<FilterColumn, Any?>, where: Boolean): Int
    fun delete(tableName: TableName, filterMap: Map<FilterColumn, Any?>): Int
    fun bulkDelete(tableName: TableName, pks: List<Map<String, Any?>>)
}