package loader

import dsl.TitleRatings
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import tsv.Reader
import kotlin.system.measureTimeMillis


object TitleRatingsLoader {

    fun load(db: Database){
        println("Loading Title Ratings")

        val time = measureTimeMillis() { // duplication

            TableLoader.process(db,
                TitleRatings,
                "./datasets/title.ratings.tsv",
                1,
                insert()
            )
        }
        println("Time was : ${time / 1000 / 60 } minutes ${time / 1000 % 60 } seconds")
    }
}


private fun insert(): BatchInsertStatement.(String) -> Unit {
    return {
        val items = it.split("\t")

        this[TitleRatings.tconst] = items[0]
        this[TitleRatings.averageRating] = if (items[1] != Reader.NO_DATA) items[1].toFloat() else null
        this[TitleRatings.numVotes] = if (items[2] != Reader.NO_DATA) items[2].toInt() else null
    }
}