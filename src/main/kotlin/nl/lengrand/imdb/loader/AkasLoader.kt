package nl.lengrand.imdb.loader

import nl.lengrand.imdb.dsl.Akas
import nl.lengrand.imdb.loader.generic.TableLoader
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import nl.lengrand.imdb.tsv.Reader
import kotlin.system.measureTimeMillis


object AkasLoader {

    fun load(db: Database){
        println("########## Loading Akas ##########")

        val time = measureTimeMillis() { // duplication

            TableLoader.process(db,
                Akas,
                "./datasets/title.akas.tsv",
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

        this[Akas.tconst] = items[0]
        this[Akas.ordering] = items[1].toInt()
        this[Akas.title] = items[2]
        this[Akas.region] = items[3]
        this[Akas.language] = items[4]
        this[Akas.types] = if (items[5] != Reader.NO_DATA) items[5] else null
        this[Akas.attributes] = if (items[6] != Reader.NO_DATA) items[6] else null
        this[Akas.isOriginalTitle] = items[7].toBoolean()
    }
}