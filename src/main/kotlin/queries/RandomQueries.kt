package queries

import dsl.Ratings
import dsl.Titles
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.system.measureTimeMillis

fun main(){
    val db = Database.connect(
        "jdbc:mysql://localhost:3306/imdb?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true",
        driver = "com.mysql.jdbc.Driver",
        user = "root",
        password = ""
    )

    // Find a actors with a given name
//    transaction(db) {
//        var result = Names.select { Names.primaryName like "%cotillard" }.toList()
//        println(result.size)
//    }

    // Find all the movies someone played in
    // Not possible currently, need join table

    // Find all the batman movies
//    transaction(db) {
//        var result = Titles.select { Titles.primaryTitle like "%batman%"}.toList()
//        println(result.size)
//        println(result)
//        println(result.first())
//    }

    // Find the rating for a specific movie
    println("Ratings query took : ${measureTimeMillis() {
        transaction(db) {
            var result = (Titles crossJoin Ratings).slice(Titles.primaryTitle, Titles.titleType, Ratings.averageRating, Ratings.numVotes).select {
                ((Titles.primaryTitle like "The Lego Batman Movie") and (Titles.titleType like "movie")
                        and Titles.tconst.eq(Ratings.tconst))
            }.toList()

            println(result.size)
            println(result)
            println(result.first())
        }        
    }}");

    println("Ratings query took : ${measureTimeMillis() {
        transaction(db) {
            var result = (Titles innerJoin Ratings).slice(Titles.primaryTitle, Titles.titleType, Ratings.averageRating, Ratings.numVotes).select {
                ((Titles.primaryTitle like "The Lego Batman Movie") and (Titles.titleType like "movie")
                        and Titles.tconst.eq(Ratings.tconst))
            }.toList()

            println(result.size)
            println(result)
            println(result.first())
        }
    }}");

}