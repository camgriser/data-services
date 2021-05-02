package org.acg.db.service.pg

import org.acg.db.service.AbstractDBCreateRepo
import org.acg.db.service.AbstractDBDeleteRepo
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PGDeleteRepoImpl (
    jdbc: NamedParameterJdbcTemplate,
    createRepo: AbstractDBCreateRepo
): AbstractDBDeleteRepo(jdbc, createRepo)