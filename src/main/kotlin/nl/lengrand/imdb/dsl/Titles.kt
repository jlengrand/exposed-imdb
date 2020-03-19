package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Titles : IntIdTable() {
    val tconst: Column<String> = (varchar("tconst", 10) references Ratings.tconst).uniqueIndex()
    val titleType: Column<String> = varchar("titleType", 50) // TODO: Own Table?
    val primaryTitle: Column<String> = varchar("primaryTitle", 500)
    val originalTitle: Column<String> = varchar("originalTitle", 500)
    val isAdult: Column<Boolean> = bool("isAdult")
    val startYear: Column<Int?> = integer("startYear").nullable()
    val endYear: Column<Int?> = integer("endYear").nullable()
    val runtimeMinutes: Column<Long?> = long("runtimeMinutes").nullable()
    val genres: Column<String> = varchar("genres", 50) // TODO: Own Table?
}