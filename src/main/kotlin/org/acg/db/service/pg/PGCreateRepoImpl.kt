package org.acg.db.service.pg

import org.acg.db.service.AbstractDBCreateRepo
import org.acg.db.service.model.BulkInsertConfig
import org.acg.db.service.model.SelectTable
import org.apache.hadoop.fs.Path
import org.postgresql.copy.CopyManager
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PGCreateRepoImpl(
    jdbc: NamedParameterJdbcTemplate,
    private val copyManager: CopyManager
) : AbstractDBCreateRepo(jdbc) {

    override fun bulkInsert(config: BulkInsertConfig) {
        val filePath = Path(config.filePath)
        copyManager.copyIn(getCopyCommand(config), )
    }

    private fun getCopyCommand(config: BulkInsertConfig): String = """
        COPY ${getTableNameAndCols(config.table)} FROM STDIN
        USING DELIMITERS '${config.delim}' WITH NULL AS '${config.nullValue}'
    """.trimIndent()

    private fun getTableNameAndCols(table: SelectTable) =
        "${table.tableName} (${table.columns.joinToString(",")})"
}