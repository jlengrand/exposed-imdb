package dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertIgnore
import tsv.Reader.NO_DATA

object TitleRatings : Table(){
    val tconst : Column<String> = varchar("tconst", 10)//.uniqueIndex()
    val averageRating : Column<Float?> = float("averageRating").nullable()
    val numVotes : Column<Int?> = integer("numVotes").nullable()

    override val primaryKey = PrimaryKey(tconst, name = "tconst")
}