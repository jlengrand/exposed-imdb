package loader

import dsl.Crews
import loader.generic.TableLoader
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import kotlin.system.measureTimeMillis


object CrewsLoader {

    fun load(db: Database){
        println("########## Loading Crews ##########")

        val time = measureTimeMillis() { // duplication

            TableLoader.process(db,
                Crews,
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

        this[Crews.tconst] = items[0]
        this[Crews.directors] = items[1]
        this[Crews.writers] = items[2]
    }
}