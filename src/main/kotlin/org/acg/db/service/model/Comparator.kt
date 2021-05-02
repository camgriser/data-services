package org.acg.db.service.model

enum class Comparator(val value: String) {
    LT("<"),
    GT(">"),
    LTE("<="),
    GTE(">="),
    EQ("="),
    LIKE("like"),
    iLIKE("ilike"),
    IN("in")
}