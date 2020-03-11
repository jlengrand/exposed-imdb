package dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Episodes : Table(){
    val tconst : Column<String> = Episodes.varchar("tconst", 10).uniqueIndex()
    val parentTconst : Column<String> = Episodes.varchar("parentTconst", 10)
    val seasonNumber : Column<Int?> = Episodes.integer("seasonNumber").nullable()
    val episodeNumber : Column<Int?> = Episodes.integer("episodeNumber").nullable()

    override val primaryKey = PrimaryKey(tconst, name = "tconst")

}