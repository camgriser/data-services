package org.acg.db.service.type

import java.io.Serializable

enum class DataType (
    val dataTypeId: Int,
    val displayName: String,
    val pgName: String
): Serializable {
    BIGINT(1, "bigint", "bigint"),
    BOOLEAN(2, "boolean", "boolean"),
    VARCHAR(3, "varchar", "varchar"),
    DATE(4, "date", "date"),
    DOUBLE(5, "double", "double"),
    INTEGER(6, "integer", "integer"),
    UUID(7, "uuid", "uuid"),
    TIMESTAMP(8, "timestamp", "timestamp"),
    DECIMAL(10, "decimal", "decimal"),
    UNKNOWN(9, "unknown", "unknown");
}

fun String.toDBTypeByPG(): DataType {
    DataType.values().toList().forEach {
        if (it.pgName == this)
            return it
    }
    return DataType.UNKNOWN
}

fun String.toDBType(): DataType {
    DataType.values().toList().forEach {
        if (it.displayName == this)
            return it
    }
    return DataType.UNKNOWN
}

fun List<String>.toDBTypeList(): List<DataType> {
    val list = ArrayList<DataType>()
    this.forEach {
        list.add(it.toDBType())
    }
    return list
}