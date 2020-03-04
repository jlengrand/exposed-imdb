package loader

import org.jetbrains.exposed.sql.Database

fun main(){

    var db = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
    println("Running loader")
}