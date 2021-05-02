package org.acg.db.sql.builder

class InsertSQLBuilder {

    companion object {
        fun generateInsertSQL(name: String, rows: List<Map<String, Any?>>): Pair<String, Map<String, Any?>> {
            var sql = ""
            val paramMap = HashMap<String, Any?>()
            val values = ArrayList<String>()
            if (rows.isNotEmpty()) {
                val columns = rows[0].keys.joinToString(", ")
                sql = "INSERT INTO $name ($columns) VALUE "
                for ((i, row) in rows.withIndex()) {
                    values.add(convert(i, row))
                    for (key in row.keys)
                        paramMap["${key}_${i}"] = row[key]
                }
                sql = sql.plus(values.joinToString(","))
            }
            return Pair(sql, paramMap)
        }

        private fun convert(i: Int, row: Map<String, Any?>): String {
            return "( ${row.keys.toList().joinToString { ":${it}_${i}" }} )"
        }
    }
}