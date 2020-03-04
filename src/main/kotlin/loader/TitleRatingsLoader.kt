package loader

import dsl.TitleRatings
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
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

        val loader = AtomicInteger()
        transaction {
            nameBasicsReader.lines().forEach {
                TitleRatings.insertFromListString(it.split("\t"))
                if (loader.incrementAndGet() % 10000 == 0) println(it)
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