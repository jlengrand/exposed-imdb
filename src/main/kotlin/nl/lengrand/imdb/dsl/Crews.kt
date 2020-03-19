package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Crews : Table(){
    val tconst = varchar("tconst", 10).uniqueIndex()
    val directors = text("directors") // TODO:  better
    val writers = text("writers") // TODO: better

    override val primaryKey = PrimaryKey(tconst)

}