package org.acg.db.service

import org.acg.db.service.intr.DBDeleteRepo
import org.acg.db.service.model.FilterColumn
import org.acg.db.service.model.SelectTable
import org.acg.db.service.model.TableName
import org.acg.db.sql.builder.SQLBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

abstract class AbstractDBDeleteRepo(
    private val jdbc: NamedParameterJdbcTemplate,
    private val createRepo: AbstractDBCreateRepo
): DBDeleteRepo {

    override fun delete(sql: String, filterMap: Map<String, Any?>): Int =
        jdbc.update(sql, filterMap)

    override fun delete(tableName: TableName, filterMap: Map<FilterColumn, Any?>): Int {
        val sql = SQLBuilder().generateDeleteSQL(tableName).build()
        return delete(sql, filterMap, filterMap.isNotEmpty())
    }

    override fun delete(sql: String, filterMap: Map<FilterColumn, Any?>, where: Boolean): Int {
        val finalSQL = SQLBuilder(sql).generateFilterSQL(filterMap.keys, where).build()
        return delete(finalSQL, SQLBuilder.convertFilterMap(filterMap))
    }

    override fun bulkDelete(tableName: TableName, pks: List<Map<String, Any?>>) {
        if (pks.isNotEmpty()) {
            // Create Temp Table
            val tempTableName = generateTempTableName()
            val selectStatement = SelectTable(pks[0].keys.toList(), tableName)
            jdbc.update(generateTempTableDeleteSQL(tempTableName, selectStatement), emptyMap<String, Any?>())

            // Upload PKs
            createRepo.bulkInsert(tableName, pks, 1, 1028)

            // Bulk Delete
            val clauses = HashSet<String>()
            pks[0].keys.forEach {
                clauses.add("t.${it} = tmp.${it}")
            }
            jdbc.update(generateBulkDeleteSQL(tableName, tempTableName, clauses), emptyMap<String, Any?>())
        }
    }

    private fun generateTempTableName(): String = "tmp_bulk_delete_${System.currentTimeMillis()}"

    private fun generateTempTableDeleteSQL(tempTableName: String, selectTable: SelectTable): String = """
        CREATE TABLE $tempTableName as
        ${SQLBuilder().generateSelectSQL(selectTable).generateLimitSQL(0)}
    """.trimIndent()

    private fun generateBulkDeleteSQL(tableName: TableName, tempTableName: String, clauses: Set<String>): String = """
        DELETE FROM $tableName t
        USING $tempTableName tmp
        WHERE ${clauses.joinToString(" and ")}
    """.trimIndent()
}