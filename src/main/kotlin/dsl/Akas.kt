package dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Akas : Table(){
    val tconst : Column<String> = varchar("tconst", 10)
    val ordering : Column<Int> = integer("ordering")
    val title : Column<String> = text("title")
    val region : Column<String> = text("region")
    val language : Column<String> = varchar("language", 3)
    val types : Column<String?> = varchar("types", 100).nullable()
    val attributes : Column<String?> = varchar("attributes", 100).nullable()
    val isOriginalTitle : Column<Boolean> = bool("isOriginalTitle")

    override val primaryKey = PrimaryKey(tconst, ordering)
}
