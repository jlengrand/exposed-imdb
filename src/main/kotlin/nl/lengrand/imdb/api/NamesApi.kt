package nl.lengrand.imdb.api

import nl.lengrand.imdb.dsl.Names
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class NamesApi {
    companion object{
        fun get(db: Database, searchTerm: String){
            transaction(db) {
                Names.select { Names.primaryName like searchTerm }.toList()
            }
        }
    }
}