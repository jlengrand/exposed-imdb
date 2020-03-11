package dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.api.ExposedBlob

object Crew : Table(){
    val tconst : Column<String> = varchar("tconst", 10).uniqueIndex()
    val directors : Column<String> = text("directors") // TODO:  better
    val writers : Column<String> = text("writers") // TODO: better

    override val primaryKey = PrimaryKey(tconst, name = "tconst")

}