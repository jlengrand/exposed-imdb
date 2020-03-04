package loader

import dsl.Titles
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.util.concurrent.atomic.AtomicInteger


class TitleBasicsLoader(private val db: Database) {

    fun createTable(){
        transaction(db) { SchemaUtils.create (Titles) }
    }

    fun loadData(){
        val nameBasicsReader = File("./datasets/title.basics.tsv").bufferedReader()
        nameBasicsReader.readLine()

        println("Title.Basics loaded")

        val loader = AtomicInteger()
        transaction {
            nameBasicsReader.lines().forEach {
                Titles.insertFromListString(it.split("\t"))
                if (loader.incrementAndGet() % 100 == 0) println(it)
            }

        }

        println("Done loading title basics!")
    }


    fun showSome(){
        transaction(db) {
            Titles.select { Titles.tconst eq "tt0000001" }.map { println(it) }
            Titles.select { Titles.tconst eq "tt9916880" }.map { println(it) }
        }
    }
}