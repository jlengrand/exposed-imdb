package loader

import dsl.Titles
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import tsv.Reader
import kotlin.system.measureTimeMillis

object TitleBasicsLoader{

    fun load(db: Database){
        println("Loading Title Basics")

        val time = measureTimeMillis() { // duplication

            TableLoader.process(db,
                Titles,
                "./datasets/title.basics.tsv",
                50,
                insert()
            )
        }
        println("Time was : ${time / 1000 / 60 } minutes ${time / 1000 % 60 } seconds")
    }
}

private fun insert(): BatchInsertStatement.(String) -> Unit {
    return {
        val items = it.split("\t")

        this[Titles.tconst] = items[0]
        this[Titles.titleType] = items[1]
        this[Titles.primaryTitle] = items[2]
        this[Titles.originalTitle] = items[3]
        this[Titles.isAdult] = items[4].toBoolean()
        this[Titles.startYear] = if (items[5] != Reader.NO_DATA) items[5].toInt() else null
        this[Titles.endYear] = if (items[6] != Reader.NO_DATA) items[6].toInt() else null
        this[Titles.runtimeMinutes] = if (items[7] != Reader.NO_DATA) items[7].toLong() else null
        this[Titles.genres] = items[8]
    }
}