package loader

import dsl.TitleRatings
import dsl.Titles
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import tsv.Reader
import kotlin.system.measureTimeMillis

fun main() {

    val time = measureTimeMillis() {
//    var db = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
//    var db = Database.connect("jdbc:mysql://localhost:3308?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true", driver = "com.mysql.jdbc.Driver", user = "root", password = "aRootPassword")
        var db = Database.connect(
            "jdbc:mysql://localhost:3306?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true",
            driver = "com.mysql.jdbc.Driver",
            user = "root",
            password = ""
        )

        transaction(db) { SchemaUtils.createDatabase("imdb") }

        db = Database.connect(
            "jdbc:mysql://localhost:3306/imdb?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true",
            driver = "com.mysql.jdbc.Driver",
            user = "root",
            password = ""
        )
//        db = Database.connect("jdbc:mysql://localhost:3308/imdb?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true", driver = "com.mysql.jdbc.Driver", user = "root", password = "aRootPassword")

        TableLoader.process(db, TitleRatings, "./datasets/title.ratings.tsv", 5, load())
    }

    println("Time was : ${time / 1000 / 60 } minutes ${time / 1000 % 60 } seconds")
}


fun load(): BatchInsertStatement.(String) -> Unit {
    return {
        val items = it.split("\t")

        this[TitleRatings.tconst] = items[0]
        this[TitleRatings.averageRating] = if (items[1] != Reader.NO_DATA) items[1].toFloat() else null
        this[TitleRatings.numVotes] = if (items[2] != Reader.NO_DATA) items[2].toInt() else null
    }
}