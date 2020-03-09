package loader

import dsl.TitleRatings
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tsv.Reader
import java.io.File
import java.util.concurrent.atomic.AtomicInteger


class TitleRatingsLoader(private val db: Database) {

    fun createTable(){
        transaction(db) { SchemaUtils.create (TitleRatings) }
    }

    fun loadData(){
        val nameBasicsReader = File("./datasets/title.ratings.tsv").bufferedReader()
        nameBasicsReader.readLine()

        println("Title.Ratings loaded")
        val lines = nameBasicsReader.readLines()
        transaction(db) {

            TitleRatings.batchInsert(lines){
                val items = it.split("\t")
                this[TitleRatings.tconst] = items[0]
                this[TitleRatings.averageRating] = if (items[1] != Reader.NO_DATA) items[1].toFloat() else null
                this[TitleRatings.numVotes] = if (items[2] != Reader.NO_DATA) items[2].toInt() else null
            }
        }

        println("Done loading title ratings!")
    }


    fun showSome(){
        transaction(db) {
            TitleRatings.select { TitleRatings.tconst eq "tt0000001" }.map { println(it) }
            TitleRatings.select { TitleRatings.tconst eq "tt9916880" }.map { println(it) }
        }
    }
}