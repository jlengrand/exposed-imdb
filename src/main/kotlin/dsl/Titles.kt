package dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Titles : Table(){
    val tconst : Column<String> = varchar("tconst", 10).uniqueIndex()
    val titleType : Column<String> = varchar("titleType", 50) // Own Table?
    val primaryTitle : Column<String> = varchar("primaryTitle", 500)
    val originalTitle : Column<String> = varchar("originalTitle", 500)
    val isAdult : Column<Boolean> = bool("isAdult")
    val startYear : Column<Int?> = integer("startYear").nullable()
    val endYear : Column<Int?> = integer("endYear").nullable()
    val runtimeMinutes : Column<Long?> = long("runtimeMinutes").nullable()
    val genres : Column<String> = varchar("genres", 50) // Own Table?

    override val primaryKey = PrimaryKey(tconst, name = "tconst")
}