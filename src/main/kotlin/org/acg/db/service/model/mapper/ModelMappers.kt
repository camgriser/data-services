package org.acg.db.service.model.mapper

import org.acg.db.service.model.CreateColumn
import org.acg.db.service.model.TableName
import org.acg.db.service.type.toDBTypeByPG

fun List<Map<String, Any?>>.toColumns(): List<CreateColumn> {
    val list = ArrayList<CreateColumn>()
    this.forEach {
        list.add(
            CreateColumn(
                it["column_name"] as String,
                (it["data_type"] as String).toDBTypeByPG()
            )
        )
    }
    return list
}

fun List<Map<String, Any?>>.toTables(): List<TableName> {
    val list = ArrayList<TableName>()
    this.forEach {
        list.add(
            TableName(
                it["table_name"] as String,
                it["schema"] as String
            )
        )
    }
    return list
}

fun List<Map<String, Any?>>.toSchemas(): List<String> {
    val list = ArrayList<String>()
    this.forEach {
        list.add(
            it["schmea"] as String
        )
    }
    return list
}