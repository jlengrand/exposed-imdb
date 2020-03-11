package dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Names : Table(){
    val nconst : Column<String> = varchar("nconst", 10).uniqueIndex()
    val primaryName : Column<String> = varchar("primaryName", 300)
    val birthYear : Column<Int?> = integer("birthYear").nullable()
    val deathYear : Column<Int?> = integer("deathYear").nullable()
    val primaryProfession : Column<String> = varchar("primaryProfession", 500)
    val knownForTitles : Column<String> = varchar("knownForTitles", 200) // TODO: Improve!

    override val primaryKey = PrimaryKey(nconst)
}