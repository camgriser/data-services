package org.acg.db.service.pg

import org.acg.db.service.AbstractDBReadRepo
import org.acg.db.service.model.BulkExportConfig
import org.acg.db.service.model.SelectTable
import org.acg.db.sql.builder.SelectSQLBuilder.Companion.generateSelectSQL
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.postgresql.copy.CopyManager
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class PGReadRepoImpl(
    jdbc: NamedParameterJdbcTemplate,
    private val copyManager: CopyManager
) : AbstractDBReadRepo(jdbc) {

    override fun export(config: BulkExportConfig) {
        val pt = Path(config.outputPath)
        val fs: FileSystem = FileSystem.get(Configuration())
        fs.delete(pt, true)
        val bufferedWriter = BufferedWriter(OutputStreamWriter(fs.create(pt, true)))
        copyManager.copyOut(getExportQuery(config.table, config.delim), bufferedWriter)
    }

    private fun getExportQuery(table: SelectTable, delim: String): String = """
        COPY (${generateSelectSQL(table)})
        TO STDOUT WITH DELIMITER '$delim'
    """.trimIndent()
}