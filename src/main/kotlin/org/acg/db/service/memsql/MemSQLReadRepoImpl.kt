package org.acg.db.service.memsql

import org.acg.db.service.AbstractDBReadRepo
import org.acg.db.service.model.BulkExportConfig
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class MemSQLReadRepoImpl(jdbc: NamedParameterJdbcTemplate) : AbstractDBReadRepo(jdbc) {

    override fun export(config: BulkExportConfig) {
        TODO("Not yet implemented")
    }
}