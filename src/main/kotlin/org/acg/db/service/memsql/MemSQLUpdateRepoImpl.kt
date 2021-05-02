package org.acg.db.service.memsql

import org.acg.db.service.AbstractDBCreateRepo
import org.acg.db.service.AbstractDBUpdateRepo
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class MemSQLUpdateRepoImpl(
    jdbc: NamedParameterJdbcTemplate,
    createRepo: AbstractDBCreateRepo
) : AbstractDBUpdateRepo(jdbc, createRepo)