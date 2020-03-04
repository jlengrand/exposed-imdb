package dsl

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Titles : IntIdTable(){
    val tconst : Column<String> = varchar("tconst", 10).uniqueIndex()
    val titleType : Column<String> = varchar("titleType", 50) // Own Table?
    val primaryTitle : Column<String> = varchar("primaryTitle", 50)
    val originalTitle : Column<String> = varchar("originalTitle", 50)
    val isAdult : Column<Boolean> = bool("isAdult")
    val startYear : Column<Int> = integer("startYear")
    val endYear : Column<Int?> = integer("endYear").nullable()
    val runtimeMinutes : Column<Long> = long("runtimeMinutes")
    val genres : Column<String> = varchar("genres", 50) // Own Table?
}

class Title(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Title>(Titles)

    var tconst by Titles.tconst
    var titleType by Titles.titleType
    var primaryTitle by Titles.primaryTitle
    var originalTitle by Titles.originalTitle
    var isAdult by Titles.isAdult
    var startYear by Titles.startYear
    var endYear by Titles.endYear
    var runtimeMinutes by Titles.runtimeMinutes
    var genres by Titles.genres
}