package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Titles : IntIdTable() {
    val tconst = (varchar("tconst", 10) references Ratings.tconst).uniqueIndex()
    val titleType = varchar("titleType", 50) // TODO: Own Table?
    val primaryTitle = varchar("primaryTitle", 500)
    val originalTitle = varchar("originalTitle", 500)
    val isAdult = bool("isAdult")
    val startYear = integer("startYear").nullable()
    val endYear = integer("endYear").nullable()
    val runtimeMinutes = long("runtimeMinutes").nullable()
    val genres = varchar("genres", 50) // TODO: Own Table?
}