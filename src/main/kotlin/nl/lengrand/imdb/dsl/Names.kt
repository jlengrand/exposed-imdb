package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Names : IntIdTable(){
    val nconst = (varchar("nconst", 10) references KnownForTitles.nconst).uniqueIndex()
    val primaryName = varchar("primaryName", 300).index()
    val birthYear = integer("birthYear").nullable()
    val deathYear= integer("deathYear").nullable()
    val primaryProfession  = varchar("primaryProfession", 500)
}

object KnownForTitles : Table(){
    val id = integer("id").autoIncrement() // Column<Int>
    val nconst = varchar("nconst", 10).index("knownfor_names")
    val tconst = (varchar("tconst", 10) references Titles.tconst).index("knownfor_titles")

    override val primaryKey = PrimaryKey(id)
}