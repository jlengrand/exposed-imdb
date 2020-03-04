package dsl

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.insert
import tsv.Reader.NO_DATA

object Titles : IntIdTable(){
    val tconst : Column<String> = varchar("tconst", 10).uniqueIndex()
    val titleType : Column<String> = varchar("titleType", 50) // Own Table?
    val primaryTitle : Column<String> = varchar("primaryTitle", 500)
    val originalTitle : Column<String> = varchar("originalTitle", 500)
    val isAdult : Column<Boolean> = bool("isAdult")
    val startYear : Column<Int?> = integer("startYear").nullable()
    val endYear : Column<Int?> = integer("endYear").nullable()
    val runtimeMinutes : Column<Long?> = long("runtimeMinutes").nullable()
    val genres : Column<String> = varchar("genres", 50) // Own Table?

    fun insertFromListString(values : List<String>){
            Titles.insert {
                it[tconst] = values[0]
                it[titleType] = values[1]
                it[primaryTitle] = values[2]
                it[originalTitle] = values[3]
                it[isAdult] = values[4].toBoolean()
                it[startYear] = if (values[5] != NO_DATA) values[5].toInt() else null
                it[endYear] = if (values[6] != NO_DATA) values[6].toInt() else null
                it[runtimeMinutes] = if (values[7] != NO_DATA) values[7].toLong() else null
                it[genres] = values[8]
            }
    }
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