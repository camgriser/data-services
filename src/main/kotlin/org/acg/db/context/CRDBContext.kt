package org.acg.db.context

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.minio.MinioClient
import org.acg.db.context.config.DBConfig
import org.acg.db.context.config.MinioConfig
import javax.sql.DataSource

/* CRDB: Example Context */
class CRDBContext(val minioConfig: MinioConfig, val dbConfig: DBConfig) {

    fun minioClient(): MinioClient =
        MinioClient.builder()
            .endpoint("https://${minioConfig.host}")
            .credentials(minioConfig.accessKey, minioConfig.secretKey)
            .build()

    fun dataSource(): DataSource =
        HikariDataSource(hikariConfig())

    private fun hikariConfig(): HikariConfig {
        val config = HikariConfig()
        config.connectionTimeout = 60*1000
        config.driverClassName = dbConfig.driver
        config.jdbcUrl = dbConfig.jdbcUrl
        config.username = dbConfig.username
        config.password = dbConfig.password
        config.connectionTestQuery = "SELECT 1"
        config.transactionIsolation = "TRANSACTION_READ_COMMITTED"
        config.addDataSourceProperty("cachePrepStmts", "true")
        config.addDataSourceProperty("prepStmtCacheSize", "250")
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        return config
    }
}