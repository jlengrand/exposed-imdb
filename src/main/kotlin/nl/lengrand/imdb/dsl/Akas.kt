package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Akas : Table(){
    val tconst = varchar("tconst", 10)
    val ordering = integer("ordering")
    val title = text("title")
    val region= text("region")
    val language  = varchar("language", 3)
    val types  = varchar("types", 100).nullable()
    val attributes = varchar("attributes", 100).nullable()
    val isOriginalTitle  = bool("isOriginalTitle")

    override val primaryKey = PrimaryKey(
        tconst,
        ordering
    )
}
