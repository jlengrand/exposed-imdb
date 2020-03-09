package dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object TitleRatings : Table(){
    val tconst : Column<String> = varchar("tconst", 10).uniqueIndex()
    val averageRating : Column<Float?> = float("averageRating").nullable()
    val numVotes : Column<Int?> = integer("numVotes").nullable()

    override val primaryKey = PrimaryKey(tconst, name = "tconst")

}