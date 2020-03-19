package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Ratings : IntIdTable(){
    val tconst = varchar("tconst", 10).uniqueIndex()
    val averageRating = float("averageRating").nullable()
    val numVotes = integer("numVotes").nullable()
}