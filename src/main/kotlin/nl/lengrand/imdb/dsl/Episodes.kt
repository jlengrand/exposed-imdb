package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Episodes : Table(){
    val tconst : Column<String> = varchar("tconst", 10).uniqueIndex()
    val parentTconst : Column<String> = varchar("parentTconst", 10)
    val seasonNumber : Column<Int?> = integer("seasonNumber").nullable()
    val episodeNumber : Column<Int?> = integer("episodeNumber").nullable()

    override val primaryKey = PrimaryKey(tconst)

}