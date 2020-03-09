package loader

import dsl.Titles
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import tsv.Reader
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
        val lines = nameBasicsReader.readLines()

        val partitions = lines.size / 50
        println(partitions)

        val listOfLists = lines.chunked(partitions)


        for(listOfList in listOfLists){
            println("Chugging list")
            println("--------")

            transaction(db) {
                Titles.batchInsert(listOfList){
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

                    if (loader.incrementAndGet() % 25000 == 0) println(items[0])
                }
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