package org.acg.db.service.crdb

import org.acg.db.service.AbstractDBCreateRepo
import org.acg.db.service.model.BulkInsertConfig
import org.acg.db.service.upload.MinioUploader
import org.apache.hadoop.fs.Path
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class CRDBCreateRepoImpl(
    private val jdbc: NamedParameterJdbcTemplate,
    private val minio: MinioUploader
): AbstractDBCreateRepo(jdbc) {

    override fun bulkInsert(config: BulkInsertConfig) {
        // Upload File to Minio for Importing
        val filePath = Path(config.filePath)
        val objectPath = minio.upload(filePath.toUri().relativize(filePath.parent.toUri()).path, filePath.name)

        val importSQL = """
            IMPORT INTO ${config.table.tableName} (${config.table.columns.joinToString(",")})
            CSV DATA ( '${minio.getImportUrl(objectPath)}' ) WITH DELIMITER = '${config.delim}'
        """.trimIndent()

        jdbc.query(importSQL) { _, _ -> }
        minio.delete(objectPath)
    }
}