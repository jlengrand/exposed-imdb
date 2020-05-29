package nl.lengrand.imdb

//-XX:StartFlightRecording=duration=6000s,filename=myheavyrecording.jfr
import nl.lengrand.imdb.dsl.Ratings
import nl.lengrand.imdb.dsl.Titles
import nl.lengrand.imdb.dsl.Titles.primaryTitle
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


    repeat(100){
        println("Ratings query took : ${measureTimeMillis() {
            transaction(db) {
                var result = (Titles crossJoin Ratings).slice(Titles.primaryTitle, Titles.titleType, Ratings.averageRating, Ratings.numVotes).select {
                    ((Titles.primaryTitle like "%batman%") and (Titles.titleType like "movie")
                            and Titles.tconst.eq(Ratings.tconst))
                }
                    .orderBy(Ratings.averageRating)
                    .toList()

                println(result.size)
                println(result)
                println(result.last()[primaryTitle])
            }
        }}");
    }
}