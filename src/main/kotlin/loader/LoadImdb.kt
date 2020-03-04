package loader

import org.jetbrains.exposed.sql.Database

fun main(){

    var db = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
//    var db = Database.connect("jdbc:mysql://localhost:3308/imdb", driver = "com.mysql.jdbc.Driver", user = "root", password = "aRootPassword")
    println("Running loader")

    val titleRatings = TitleRatingsLoader(db)
    titleRatings.createTable()
    titleRatings.loadData()
    titleRatings.showSome()
}