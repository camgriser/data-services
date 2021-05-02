package org.acg.db.service.crdb

import org.acg.db.service.AbstractDBReadRepo
import org.acg.db.service.model.BulkExportConfig
import org.acg.db.service.upload.MinioUploader
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class CRDBReadRepoImpl(
    jdbc: NamedParameterJdbcTemplate,
    private val minio: MinioUploader
): AbstractDBReadRepo(jdbc) {

    override fun export(config: BulkExportConfig) {
        val exportSQL = """
            EXPORT INTO CSV '${minio.baseUrl()}/${config.outputPath}'
            WITH delimiter = '${config.delim}' FROM
            SELECT ${config.table.columns.joinToString(",")} FROM ${config.table.tableName};
        """.trimIndent()
        fetch(exportSQL, emptyMap())
    }
}