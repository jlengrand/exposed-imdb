package dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Crews : Table(){
    val tconst : Column<String> = varchar("tconst", 10).uniqueIndex()
    val directors : Column<String> = text("directors") // TODO:  better
    val writers : Column<String> = text("writers") // TODO: better

    override val primaryKey = PrimaryKey(tconst)

}