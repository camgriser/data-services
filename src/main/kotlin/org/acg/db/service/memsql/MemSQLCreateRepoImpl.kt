package org.acg.db.service.memsql

import org.acg.db.service.AbstractDBCreateRepo
import org.acg.db.service.model.BulkInsertConfig
import org.apache.hadoop.fs.Path
import org.mariadb.jdbc.MariaDbStatement
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.io.File
import java.io.FileInputStream

class MemSQLCreateRepoImpl (private val jdbc: NamedParameterJdbcTemplate) : AbstractDBCreateRepo(jdbc) {

    override fun bulkInsert(config: BulkInsertConfig) {
        val dataSource = jdbc.jdbcTemplate.dataSource
        if (dataSource != null) {
            val stmt: MariaDbStatement = dataSource.connection.createStatement() as MariaDbStatement
            val filePath = Path(config.filePath)
            val loadCmd = """
                load data local inline '' into table ${config.table.tableName}
                COLUMNS TERMINATED BY '${config.delim}' (${config.table.columns.joinToString(",")})
            """.trimIndent()
            stmt.setLocalInfileInputStream(FileInputStream(File(filePath.toUri())))
            stmt.execute(loadCmd)
        }
    }
}