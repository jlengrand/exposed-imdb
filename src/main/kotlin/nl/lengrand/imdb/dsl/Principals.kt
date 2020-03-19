package nl.lengrand.imdb.dsl

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Principals : Table() {
    val tconst: Column<String> = varchar("tconst", 10)
    val ordering: Column<Int> = integer("ordering")
    val nconst: Column<String> = varchar("nconst", 10)
    val category: Column<String> = varchar("category", 50)
    val job: Column<String?> = text("job").nullable()
    val characters: Column<String?> = text("characters").nullable()

    override val primaryKey = PrimaryKey(
        tconst,
        ordering,
        nconst
    )
}