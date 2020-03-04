package dsl

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import tsv.Reader.NO_DATA

object TitleRatings : Table(){
    val tconst : Column<String> = varchar("tconst", 10).uniqueIndex()
    val averageRating : Column<Float?> = float("averageRating").nullable()
    val numVotes : Column<Int?> = integer("numVotes").nullable()

    fun insertFromListString(values : List<String>){
        TitleRatings.insert {
                it[tconst] = values[0]
                it[averageRating] = if (values[1] != NO_DATA) values[1].toFloat() else null
                it[numVotes] = if (values[2] != NO_DATA) values[2].toInt() else null
            }
    }
}

//class TitleRating(id: EntityID<Int>) : IntEntity(id) {
//    companion object : IntEntityClass<TitleRating>(TitleRatings)
//
//    var tconst by TitleRatings.tconst
//    var averageRating by TitleRatings.averageRating
//    var numVotes by TitleRatings.numVotes
//}