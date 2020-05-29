package nl.lengrand.imdb

import nl.lengrand.imdb.loader.NamesLoader
import nl.lengrand.imdb.loader.RatingsLoader
import nl.lengrand.imdb.loader.TitleLoader
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.system.measureTimeMillis

fun main() {

//    var db = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
    var db = Database.connect(
        "jdbc:mysql://localhost:3308?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = "aRootPassword"
    )

    transaction(db) { SchemaUtils.dropDatabase("imdb") }
    transaction(db) { SchemaUtils.createDatabase("imdb") }

    db = Database.connect(
        "jdbc:mysql://localhost:3308/imdb?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = "aRootPassword"
    )

    val time = measureTimeMillis() { // duplication

        // TODO: Avoid overwriting

        RatingsLoader.load(db)
        TitleLoader.load(db)
        NamesLoader.load(db)
        //    CrewsLoader.load(db)
        //    EpisodesLoader.load(db)
        //    PrincipalsLoader.load(db)  // Time was : 21 minutes 23 seconds
        //    AkasLoader.load(db) // Time was : 10 minutes 39 seconds
    }
    println("---- Total time")
    println("Total Time was : ${time / 1000 / 60 } minutes ${time / 1000 % 60 } seconds")
}