package org.acg.db.service

import org.acg.db.service.intr.DBReadRepo
import org.acg.db.service.model.BulkExportConfig
import org.acg.db.service.model.FilterColumn
import org.acg.db.service.model.SelectTable
import org.acg.db.service.model.SortColumn
import org.acg.db.sql.builder.SQLBuilder
import org.acg.db.sql.mapper.GenericMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

abstract class AbstractDBReadRepo(private val jdbc: NamedParameterJdbcTemplate): DBReadRepo {

    override fun fetch(sql: String, filterMap: Map<String, Any?>): List<Map<String, Any?>> =
        jdbc.query(sql, filterMap, GenericMapper())

    override fun fetch(sql: String, filterMap: Map<FilterColumn, Any?>, where: Boolean): List<Map<String, Any?>> {
        val finalSQL = SQLBuilder(sql).generateFilterSQL(filterMap.keys, where).build()
        return fetch(finalSQL, SQLBuilder.convertFilterMap(filterMap))
    }

    override fun fetch(table: SelectTable, filterMap: Map<FilterColumn, Any?>): List<Map<String, Any?>> {
        val sql = SQLBuilder().generateSelectSQL(table).build()
        return fetch(sql,filterMap, filterMap.isNotEmpty())
    }

    override fun page(sql: String, filterMap: Map<FilterColumn, Any?>, orderMap: List<SortColumn>, where: Boolean
                      , pageSize: Int): List<Map<String, Any?>> {
        val finalSQL = SQLBuilder(sql).generateFilterSQL(filterMap.keys, where)
            .generateSortingSQL(orderMap)
            .generateLimitSQL(pageSize).build()
        return fetch(finalSQL, SQLBuilder.convertFilterMap(filterMap))
    }

    override fun page(table: SelectTable, filterMap: Map<FilterColumn, Any?>, orderMap: List<SortColumn>): List<Map<String, Any?>> {
        val sql = SQLBuilder().generateSelectSQL(table)
            .generateFilterSQL(filterMap.keys, filterMap.isNotEmpty())
            .generateSortingSQL(orderMap)
            .build()
        return fetch(sql, SQLBuilder.convertFilterMap(filterMap))
    }

    override fun page(table: SelectTable, filterMap: Map<FilterColumn, Any?>, orderMap: List<SortColumn>, pageSize: Int): List<Map<String, Any?>> {
        val sql = SQLBuilder().generateSelectSQL(table)
            .generateFilterSQL(filterMap.keys, filterMap.isNotEmpty())
            .generateSortingSQL(orderMap)
            .build()
        return fetch(sql, SQLBuilder.convertFilterMap(filterMap))
    }

    abstract override fun export(config: BulkExportConfig)
}