package nl.lengrand.imdb.api

import nl.lengrand.imdb.dsl.Names
import org.jetbrains.exposed.sql.select

class NamesApi {
    companion object{
        fun get(searchTerm: String){
            Names.select { Names.primaryName like "%cotillard" }.toList()
        }
    }
}