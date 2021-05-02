package org.acg.db.service.model

import org.acg.db.service.type.DataType
import java.io.Serializable

data class CreateColumn (
    var name: String,
    var type: DataType
): Serializable