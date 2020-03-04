package loader

import dsl.Titles
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

val NO_DATA = "\\N";

class Titlebasics(private val db: Database) {

    fun createTable(){
        transaction(db) { SchemaUtils.create (Titles) }
    }

    fun loadData(){
        val nameBasicsReader = File("./datasets/title.basics.tsv").bufferedReader()
        nameBasicsReader.readLine()

        println("Title.Basics loaded")

        for (ptr in 1..10){
            val line = nameBasicsReader.readLine()
            val items = line.split("\t")
            println(items)

            transaction {
                Titles.insert {
                    it[tconst] = items[0]
                    it[titleType] = items[1]
                    it[primaryTitle] = items[2]
                    it[originalTitle] = items[3]
                    it[isAdult] = items[4].toBoolean()
                    it[startYear] = items[5].toInt()
                    it[endYear] = if (items[6] != NO_DATA) items[6].toInt() else null
                    it[runtimeMinutes] = items[7].toLong()
                    it[genres] = items[8]
                }
            }

            //        transaction {
            //            loader.Title.new {
            //                tconst = items[0]
            //                titleType = items[1]
            //                primaryTitle = items[2]
            //                originalTitle = items[3]
            //                isAdult = items[4].toBoolean()
            //                startYear = items[5].toInt()
            //                endYear = if (items[6] != dsl.getNO_DATA) items[6].toInt() else null
            //                runtimeMinutes = items[7].toLong()
            //                genres = items[8]
            //            }
            //        }

            println("--------")
        }
    }

    fun showAll() {
        transaction(db) { Titles.selectAll().map { println(it) } }
    }
}