package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Episodes : Table(){
    val tconst = varchar("tconst", 10).uniqueIndex()
    val parentTconst = varchar("parentTconst", 10)
    val seasonNumber = integer("seasonNumber").nullable()
    val episodeNumber = integer("episodeNumber").nullable()

    override val primaryKey = PrimaryKey(tconst)

}