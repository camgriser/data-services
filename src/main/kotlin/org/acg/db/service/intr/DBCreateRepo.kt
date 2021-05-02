package org.acg.db.service.intr

import org.acg.db.service.model.BulkInsertConfig
import org.acg.db.service.model.CreateTable
import org.acg.db.service.model.TableName

interface DBCreateRepo {

    fun create(createTable: CreateTable): Int
    fun insert(tableName: TableName, rows: List<Map<String, Any?>>): Int
    fun insert(sql: String, row: Map<String, Any?>): Int
    fun bulkInsert(config: BulkInsertConfig)
    fun bulkInsert(tableName: TableName, rows: List<Map<String, Any?>>, workers: Int, batchSize: Int)
}