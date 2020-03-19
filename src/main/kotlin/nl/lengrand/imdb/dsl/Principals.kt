package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Principals : Table() {
    val tconst = varchar("tconst", 10)
    val ordering = integer("ordering")
    val nconst = varchar("nconst", 10)
    val category = varchar("category", 50)
    val job = text("job").nullable()
    val characters = text("characters").nullable()

    override val primaryKey = PrimaryKey(
        tconst,
        ordering,
        nconst
    )
}