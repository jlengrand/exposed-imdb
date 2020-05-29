package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Names : IntIdTable(){
    val nconst = (varchar("nconst", 10) references KnownForTitles.nconst).uniqueIndex()
    val primaryName = varchar("primaryName", 300).index()
    val birthYear = integer("birthYear").nullable()
    val deathYear= integer("deathYear").nullable()
    val primaryProfession  = varchar("primaryProfession", 500)
}

class Name(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Name>(Names)

    var nconst by Names.nconst
    var primaryName by Names.primaryName
    var birthYear by Names.birthYear
    var deathYear by Names.deathYear
    var primaryProfession by Names.primaryProfession
}

object KnownForTitles : IntIdTable(){
//    val id = integer("id").autoIncrement() // Column<Int>
    val nconst = varchar("nconst", 10).index("knownfor_names")
    val tconst = (varchar("tconst", 10) references Titles.tconst).index("knownfor_titles")

//    override val primaryKey = PrimaryKey(id)
}

