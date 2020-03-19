package nl.lengrand.imdb.loader

import nl.lengrand.imdb.dsl.KnownForTitles
import nl.lengrand.imdb.dsl.Names
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction
import nl.lengrand.imdb.tsv.Reader
import java.io.File
import kotlin.system.measureTimeMillis

object NamesLoader{

    fun load(db: Database){
        println("########## Loading Names ##########")

        val time = measureTimeMillis() { // duplication

            transaction(db) {
                SchemaUtils.create(Names)
                SchemaUtils.create(KnownForTitles)
            }

            val reader = File("./datasets/name.basics.tsv").bufferedReader()
            reader.readLine() // pass headers

            val lines = reader.readLines()

            populateNames(lines, 500, db)
            populateKnownForTitles(lines, 50, db)

        }
        println("Time was : ${time / 1000 / 60 } minutes ${time / 1000 % 60 } seconds")
    }

    private fun populateNames(
        lines: List<String>,
        partitions: Int,
        db: Database
    ): Int {
        val partitionSize = lines.size / partitions
        val listOfLists = lines.chunked(partitionSize)

        var increment = 0
        for (listOfList in listOfLists) {
            increment += 1
            println("-------- $increment")

            transaction(db) {
                Names.batchInsert(listOfList) {
                    val items = it.split("\t")

                    this[Names.nconst] = items[0]
                    this[Names.primaryName] = items[1]
                    this[Names.birthYear] = if (items[2] != Reader.NO_DATA) items[2].toInt() else null
                    this[Names.deathYear] = if (items[3] != Reader.NO_DATA) items[3].toInt() else null
                    this[Names.primaryProfession] = items[4]
                }
            }
        }
        return increment
    }

    private fun populateKnownForTitles(
        lines: List<String>,
        partitions: Int,
        db: Database
    ) {
        // How can I do this better?
        var increment = 0
        val pairs = lines.map {
            val items = it.split("\t")
            val nconst = items[0]
            val tconsts = items[5].split(",")
                tconsts.map {
                tconst -> Pair(nconst, tconst)
            }
        }.flatten()

        val pairsPartitionSize = pairs.size / partitions
        val listofpairs = pairs.chunked(pairsPartitionSize)

        increment = 0
        for (pair in listofpairs) {
            increment += 1
            println("-------- $increment")

            transaction(db) {
                KnownForTitles.batchInsert(pair) {
                    this[KnownForTitles.nconst] = it.first
                    this[KnownForTitles.tconst] = it.second
                }
            }
        }
    }
}