package loader

import dsl.Principals
import loader.generic.TableLoader
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import tsv.Reader
import kotlin.system.measureTimeMillis


object PrincipalsLoader {

    fun load(db: Database){
        println("########## Loading Principals ##########")

        val time = measureTimeMillis() { // duplication

            TableLoader.process(db,
                Principals,
                "./datasets/title.principals.tsv",
                50000,
                insert()
            )
        }
        println("Time was : ${time / 1000 / 60 } minutes ${time / 1000 % 60 } seconds")
    }
}

private fun insert(): BatchInsertStatement.(String) -> Unit {
    return {
        val items = it.split("\t")

        this[Principals.tconst] = items[0]
        this[Principals.ordering] = items[1].toInt()
        this[Principals.nconst] = items[2]
        this[Principals.category] = items[3]
        this[Principals.job] = if (items[4] != Reader.NO_DATA) items[4] else null
        this[Principals.characters] = if (items[5] != Reader.NO_DATA) items[5] else null
    }
}