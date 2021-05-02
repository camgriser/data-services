package org.acg.db.service.pg

import org.acg.db.service.AbstractDBCreateRepo
import org.acg.db.service.AbstractDBUpdateRepo
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class PGUpdateRepoImpl (
    jdbc: NamedParameterJdbcTemplate,
    createRepo: AbstractDBCreateRepo
) : AbstractDBUpdateRepo(jdbc, createRepo)