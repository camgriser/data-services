package org.acg.db.context

import org.acg.db.context.config.DBConfig
import org.postgresql.copy.CopyManager
import org.postgresql.core.BaseConnection
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.sql.Connection
import java.sql.DriverManager
import javax.sql.DataSource

/* PG: Example Context */
class PGContext (val config: DBConfig) {

    fun copyManager(): CopyManager {
        val conn: Connection = DriverManager.getConnection(config.jdbcUrl, config.username, config.password)
        return CopyManager(conn as BaseConnection)
    }

    fun dataSource(): DataSource {
        val dataSourceBuilder = DriverManagerDataSource(config.jdbcUrl)
        dataSourceBuilder.setDriverClassName(config.driver)
        dataSourceBuilder.url = config.jdbcUrl
        dataSourceBuilder.username = config.username
        dataSourceBuilder.password = config.password
        return dataSourceBuilder
    }
}