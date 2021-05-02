package org.acg.db.service

import org.acg.db.service.batch.BatchCreator
import org.acg.db.service.intr.DBCreateRepo
import org.acg.db.service.model.BulkInsertConfig
import org.acg.db.service.model.CreateTable
import org.acg.db.service.model.TableName
import org.acg.db.sql.builder.CreateTableSQLBuilder
import org.acg.db.sql.builder.SQLBuilder
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

abstract class AbstractDBCreateRepo(private val jdbc: NamedParameterJdbcTemplate) : DBCreateRepo {

    override fun insert(sql: String, row: Map<String, Any?>): Int =
        jdbc.update(sql, row)

    override fun insert(tableName: TableName, rows: List<Map<String, Any?>>): Int {
        val sqlPair = SQLBuilder().generateInsertSQL(tableName.toString(), rows)
        return insert(sqlPair.first.build(), sqlPair.second)
    }

    override fun bulkInsert(tableName: TableName, rows: List<Map<String, Any?>>, workers: Int, batchSize: Int) {
        val allWorkerRows = rows.withIndex().groupBy { it.index % workers }
                .map { it.value.map { it.value } }
        allWorkerRows.forEach { workerRows ->
            val ranges = BatchCreator.createRange(batchSize, workerRows.size)
            ranges.forEach {
                val batchRows = workerRows.slice(it)
                insert(tableName, batchRows)
            }
        }
    }

    override fun create(createTable: CreateTable): Int =
        insert(CreateTableSQLBuilder.generateCreateTableSQL(createTable), emptyMap())

    abstract override fun bulkInsert(config: BulkInsertConfig)
}