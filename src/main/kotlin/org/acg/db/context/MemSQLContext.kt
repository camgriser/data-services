package org.acg.db.context

import org.acg.db.context.config.DBConfig
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.util.*
import javax.sql.DataSource

/* MEMSQL: Example Context */
class MemSQLContext (val config: DBConfig) {

    fun dataSource(): DataSource {
        val ds = DriverManagerDataSource(config.jdbcUrl)
        ds.setDriverClassName("org.mariadb.jdbc.Driver")
        ds.username = config.username
        ds.password = config.password

        val dsProps = Properties()
        dsProps["jdbcCompliantTruncation"] = "false"
        dsProps["useServerPrepStmts"] = "false"

        ds.connectionProperties = dsProps

        return ds
    }
}