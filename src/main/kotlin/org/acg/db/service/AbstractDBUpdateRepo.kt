package org.acg.db.service

import org.acg.db.service.intr.DBUpdateRepo
import org.acg.db.service.model.FilterColumn
import org.acg.db.service.model.SelectTable
import org.acg.db.service.model.SetColumn
import org.acg.db.service.model.TableName
import org.acg.db.sql.builder.FilterSQLBuilder
import org.acg.db.sql.builder.SQLBuilder
import org.acg.db.sql.builder.UpdateSQLBuilder
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

abstract class AbstractDBUpdateRepo(
    private val jdbc: NamedParameterJdbcTemplate,
    private val createRepo: AbstractDBCreateRepo
): DBUpdateRepo {

    override fun update(sql: String, filterMap: Map<String, Any?>): Int =
        jdbc.update(sql, filterMap)

    override fun update(sql: String, filterMap: Map<FilterColumn, Any?>, where: Boolean): Int {
        val finalSQL = SQLBuilder(sql).generateFilterSQL(filterMap.keys, where).build()
        return update(finalSQL, SQLBuilder.convertFilterMap(filterMap))
    }

    override fun update(table: TableName, setColumns: Set<SetColumn>, filterMap: Map<FilterColumn, Any?>): Int {
        val sql = SQLBuilder().generateUpdateSQL(table, setColumns)
            .generateFilterSQL(filterMap.keys, filterMap.isNotEmpty())
            .build()
        var paramMap = FilterSQLBuilder.convertFilterMap(filterMap)
        setColumns.forEach {
            paramMap = paramMap.plus(Pair(it.name, it.paramValue))
        }
        return update(sql, paramMap)
    }

    override fun bulkUpdate(tableName: TableName, setColumns: Set<SetColumn>, pks: List<Map<String, Any?>>) {
        if (pks.isNotEmpty()) {
            // Create Temp Table
            val tempTableName = generateTempTableName()
            update(generateTempTableUpdateSQL(tempTableName, SelectTable(pks[0].keys.toList(), tableName)), emptyMap())

            // Upload PKs
            createRepo.bulkInsert(tableName, pks, 1, 1028)

            // Bulk Delete
            val clauses = HashSet<String>()
            pks[0].keys.forEach {
                clauses.add("t.${it} = tmp.${it}")
            }
            update(generateBulkDeleteSQL(tableName, tempTableName, setColumns, clauses), emptyMap())
            update("DROP TABLE if exists $tempTableName", emptyMap())
        }
    }

    private fun generateTempTableName(): String = "tmp_bulk_update_${System.currentTimeMillis()}"

    private fun generateTempTableUpdateSQL(tempTableName: String, selectTable: SelectTable): String = """
        CREATE TEMP TABLE $tempTableName AS
        ${SQLBuilder().generateSelectSQL(selectTable).generateLimitSQL(0).build()}
    """.trimIndent()

    private fun generateBulkDeleteSQL(table: TableName, tempTableName: String, setColumns: Set<SetColumn>, clauses: Set<String>): String = """
        ${UpdateSQLBuilder.generateUpdateSQL(table, setColumns)}
        FROM $tempTableName tmp
        WHERE ${clauses.joinToString(" and ")}
    """.trimIndent()
}