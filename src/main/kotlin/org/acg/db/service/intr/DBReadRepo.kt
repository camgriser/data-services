package org.acg.db.service.intr

import org.acg.db.service.model.BulkExportConfig
import org.acg.db.service.model.FilterColumn
import org.acg.db.service.model.SelectTable
import org.acg.db.service.model.SortColumn

interface DBReadRepo {

    fun fetch(sql: String, filterMap: Map<String, Any?>): List<Map<String, Any?>>
    fun fetch(sql: String, filterMap: Map<FilterColumn, Any?>, where: Boolean): List<Map<String, Any?>>
    fun fetch(table: SelectTable, filterMap: Map<FilterColumn, Any?>): List<Map<String, Any?>>
    fun page(sql: String, filterMap: Map<FilterColumn, Any?>, orderMap: List<SortColumn>, where: Boolean, pageSize: Int): List<Map<String, Any?>>
    fun page(table: SelectTable, filterMap: Map<FilterColumn, Any?>, orderMap: List<SortColumn>, pageSize: Int): List<Map<String, Any?>>
    fun page(table: SelectTable, filterMap: Map<FilterColumn, Any?>, orderMap: List<SortColumn>): List<Map<String, Any?>>
    fun export(config: BulkExportConfig)
}