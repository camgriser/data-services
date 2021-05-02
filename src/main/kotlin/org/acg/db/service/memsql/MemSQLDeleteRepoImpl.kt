package org.acg.db.service.memsql

import org.acg.db.service.AbstractDBCreateRepo
import org.acg.db.service.AbstractDBDeleteRepo
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class MemSQLDeleteRepoImpl(
    jdbc: NamedParameterJdbcTemplate,
    createRepo: AbstractDBCreateRepo
) : AbstractDBDeleteRepo(jdbc, createRepo)