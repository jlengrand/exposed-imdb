package loader

import dsl.Crew
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import kotlin.system.measureTimeMillis


object CrewsLoader {

    fun load(db: Database){
        println("Loading Crews")

        val time = measureTimeMillis() { // duplication

            TableLoader.process(db,
                Crew,
                "./datasets/title.crew.tsv",
                5000,
                insert()
            )
        }
        println("Time was : ${time / 1000 / 60 } minutes ${time / 1000 % 60 } seconds")
    }
}

private fun insert(): BatchInsertStatement.(String) -> Unit {
    return {
        val items = it.split("\t")

        this[Crew.tconst] = items[0]
        this[Crew.directors] = items[1]
        this[Crew.writers] = items[2]
    }
}