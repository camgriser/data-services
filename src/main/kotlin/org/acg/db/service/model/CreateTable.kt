package org.acg.db.service.model

import java.io.Serializable

data class CreateTable (
    var columns: List<CreateColumn>,
    var tableName: TableName
): Serializable