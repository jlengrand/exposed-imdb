package loader

import dsl.Episodes
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import tsv.Reader
import kotlin.system.measureTimeMillis

object EpisodesLoader{

    fun load(db: Database){
        println("Loading Episodes")

        val time = measureTimeMillis() { // duplication

            TableLoader.process(db,
                Episodes,
                "./datasets/title.episode.tsv",
                1000,
                insert()
            )
        }
        println("Time was : ${time / 1000 / 60 } minutes ${time / 1000 % 60 } seconds")
    }
}

private fun insert(): BatchInsertStatement.(String) -> Unit {
    return {
        val items = it.split("\t")

        this[Episodes.tconst] = items[0]
        this[Episodes.parentTconst] = items[1]
        this[Episodes.seasonNumber] = if (items[2] != Reader.NO_DATA) items[2].toInt() else null
        this[Episodes.episodeNumber] = if (items[3] != Reader.NO_DATA) items[3].toInt() else null
    }
}