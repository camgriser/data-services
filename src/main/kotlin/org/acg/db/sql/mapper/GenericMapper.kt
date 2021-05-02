package org.acg.db.sql.mapper

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class GenericMapper : RowMapper<Map<String, Any?>> {

    override fun mapRow(rs: ResultSet, rowNum: Int): Map<String, Any?> {
        val metaData = rs.metaData
        val row = HashMap<String, Any?>()
        for (i in 1..metaData.columnCount) {
            row[metaData.getColumnName(i)] = rs.getObject(i)
        }
        return row
    }
}