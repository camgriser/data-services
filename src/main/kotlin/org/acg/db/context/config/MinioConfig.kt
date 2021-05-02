package org.acg.db.context.config

data class MinioConfig (
    val host: String,
    val accessKey: String,
    val secretKey: String,
    val bucket: String,
    val url: String
)