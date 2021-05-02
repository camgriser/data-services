package org.acg.db.service.model

data class BulkExportConfig (
    val table: SelectTable,
    val delim: String,
    val outputPath: String
)