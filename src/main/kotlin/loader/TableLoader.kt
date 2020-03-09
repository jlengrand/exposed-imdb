package loader

import dsl.TitleRatings
import dsl.Titles
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import tsv.Reader


fun BatchInsertStatement.loadItem(item: String){
    val items = item.split("\t")
    this[TitleRatings.tconst] = items[0]
    this[TitleRatings.averageRating] = if (items[1] != Reader.NO_DATA) items[1].toFloat() else null
    this[TitleRatings.numVotes] = if (items[2] != Reader.NO_DATA) items[2].toInt() else null
}

object TableLoader{

    fun process(db : Database, table: Table, fileName: String, partitions: Int, operation: BatchInsertStatement.(String) -> Unit){
        createTable(db, table)
        loadData(db, table, fileName, partitions, operation)
    }

    private fun createTable(db : Database, table: Table){
        transaction(db) { SchemaUtils.create (table) }
    }

    private fun loadData(db : Database, table: Table, fileName: String, partitions: Int, operation: BatchInsertStatement.(String) -> Unit){
        val reader = File(fileName).bufferedReader()
        reader.readLine() // pass headers

        val lines = reader.readLines()

        val partitionSize = lines.size / partitions
        val listOfLists = lines.chunked(partitionSize)

        var increment = 0
        for(listOfList in listOfLists){
            increment += 1
            println("-------- $increment")

            transaction(db) {
                table.batchInsert(listOfList, body = operation)
            }
        }
    }
}