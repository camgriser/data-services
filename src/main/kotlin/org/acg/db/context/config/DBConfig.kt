package org.acg.db.context.config

data class DBConfig (
    val jdbcUrl: String,
    val username: String,
    val password: String,
    val database: String,
    val driver: String
)