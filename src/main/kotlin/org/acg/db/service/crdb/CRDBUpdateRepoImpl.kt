package org.acg.db.service.crdb

import org.acg.db.service.AbstractDBCreateRepo
import org.acg.db.service.AbstractDBUpdateRepo
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class CRDBUpdateRepoImpl(
    jdbc: NamedParameterJdbcTemplate,
    createRepo: AbstractDBCreateRepo
): AbstractDBUpdateRepo(jdbc, createRepo)