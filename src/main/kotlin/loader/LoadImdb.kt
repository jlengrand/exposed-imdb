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

        TitleRatingsLoader.load(db)
        TitleBasicsLoader.load(db)
    }

    println("Time was : ${time / 1000 / 60 } minutes ${time / 1000 % 60 } seconds")
}