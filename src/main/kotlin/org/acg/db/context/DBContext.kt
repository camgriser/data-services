package org.acg.db.context

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

/* DB: Example Context */
class DBContext (val dataSource: DataSource) {

    fun jdbc(): NamedParameterJdbcTemplate {
        val jdbcTemplate = JdbcTemplate(dataSource)
        jdbcTemplate.queryTimeout = 60
        return NamedParameterJdbcTemplate(jdbcTemplate)
    }
}