package org.acg.db.service.pg

import org.acg.db.service.intr.DBReadRepo
import org.acg.db.service.intr.DBSystemInfo
import org.acg.db.service.model.CreateColumn
import org.acg.db.service.model.TableName
import org.acg.db.service.model.mapper.toColumns
import org.acg.db.service.model.mapper.toSchemas
import org.acg.db.service.model.mapper.toTables

class PGSystemInfoImpl(private val readRepo: DBReadRepo) : DBSystemInfo {

    override fun getColumns(schema: String, tableName: TableName): List<CreateColumn> =
        readRepo.fetch("""
            SELECT column_name, lower(data_type) as data_type
            FROM information_schema.columns
            WHERE table_name = :tableName
              AND table_schmea = :schema
            ORDER BY ordinal_position
        """.trimIndent(), mapOf(
            "tableName" to tableName,
            "schema" to if (schema.isNotEmpty()) schema else "public"
        )).toColumns()

    override fun getTables(schema: String, search: String, owner: String): List<TableName> =
        readRepo.fetch("""
            SELECT schemaname as schema, tablename as table_name
            FROM pg_tables
            WHERE schemaname = :schema
              AND tableowner = :owner
              AND lower(tablename) like :tableName
            ORDER BY tablename
        """.trimIndent(), mapOf(
            "tableName" to "%${search.toLowerCase()}%",
            "schema" to if (schema.isNotEmpty()) schema else "public",
            "owner" to owner
        )).toTables()

    override fun getSchemas(owner: String): List<String> =
        readRepo.fetch("""
            SELECT schema_name as schema
            FROM information_schema.schemata
            WHERE schema_owner = :owner
            ORDER BY schema_name
        """.trimIndent(), mapOf(
            "owner" to owner
        )).toSchemas()
}