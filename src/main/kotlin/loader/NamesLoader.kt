package loader

import dsl.Names
import dsl.Names.nullable
import dsl.Names.uniqueIndex
import dsl.Titles
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import tsv.Reader
import kotlin.system.measureTimeMillis

object NameLoader{

    fun load(db: Database){
        println("Loading Names Basics")

        val time = measureTimeMillis() { // duplication

            TableLoader.process(db,
                Names,
                "./datasets/name.basics.tsv",
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

        this[Names.nconst] = items[0]
        this[Names.primaryName] = items[1]
        this[Names.birthYear] = if (items[2] != Reader.NO_DATA) items[2].toInt() else null
        this[Names.deathYear] = if (items[3] != Reader.NO_DATA) items[3].toInt() else null
        this[Names.primaryProfession] = items[4]
        this[Names.knownForTitles] = items[5]
    }
}