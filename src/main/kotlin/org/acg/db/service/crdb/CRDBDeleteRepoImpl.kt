package org.acg.db.service.crdb

import org.acg.db.service.AbstractDBCreateRepo
import org.acg.db.service.AbstractDBDeleteRepo
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class CRDBDeleteRepoImpl(
    jdbc: NamedParameterJdbcTemplate,
    createRepo: AbstractDBCreateRepo
) : AbstractDBDeleteRepo(jdbc, createRepo)