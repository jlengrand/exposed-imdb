# Exposed Imdb

A simple project to learn more about [exposed](https://github.com/JetBrains/Exposed), using the [imdb datasets](https://datasets.imdbws.com/).

Please note that if you use this project, you have to comply with the [Imbd license](https://www.imdb.com/interfaces/). 

## LICENSE

In short, [CC BY-NC-SA 4.0](https://tldrlegal.com/license/creative-commons-attribution-noncommercial-sharealike-4.0-international-(cc-by-nc-sa-4.0)). 

See [LICENSE](/LICENSE)

## Learnings 


* Use `;DB_CLOSE_DELAY=-1` if you want to persist the in-memory database information over more than a single transaction.
  * `Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")`
* Use `?useSSL=false` to avoid SSL exceptions (for dev only!) on MySQL.
  * `Database.connect("jdbc:mysql://localhost:3308/imdb?useSSL=false", driver = "com.mysql.jdbc.Driver", user = "root", password = "aRootPassword")`
* Use `rewriteBatchedStatements=true` when inserting large volumes of data to have your driver rewrite your query
* `.map` keeps stack of memory while `for` loop doesn't? I get a OME when running with map
* reason to use partitions
* dsl loading copy paste prone
* Cross Join
* references

```
1
[dsl.Titles.primaryTitle=The Lego Batman Movie, dsl.Titles.titleType=movie, dsl.Ratings.averageRating=7.3, dsl.Ratings.numVotes=123790]
dsl.Titles.primaryTitle=The Lego Batman Movie, dsl.Titles.titleType=movie, dsl.Ratings.averageRating=7.3, dsl.Ratings.numVotes=123790
Ratings query took : 3170
1
[dsl.Titles.primaryTitle=The Lego Batman Movie, dsl.Titles.titleType=movie, dsl.Ratings.averageRating=7.3, dsl.Ratings.numVotes=123790]
dsl.Titles.primaryTitle=The Lego Batman Movie, dsl.Titles.titleType=movie, dsl.Ratings.averageRating=7.3, dsl.Ratings.numVotes=123790
Ratings query took : 2159

object Titles : IntIdTable() {
    val tconst: Column<String> = (varchar("tconst", 10) references Ratings.tconst).uniqueIndex()
    val titleType: Column<String> = varchar("titleType", 50) // TODO: Own Table?
    val primaryTitle: Column<String> = varchar("primaryTitle", 500)
    val originalTitle: Column<String> = varchar("originalTitle", 500)
    val isAdult: Column<Boolean> = bool("isAdult")
    val startYear: Column<Int?> = integer("startYear").nullable()
    val endYear: Column<Int?> = integer("endYear").nullable()
    val runtimeMinutes: Column<Long?> = long("runtimeMinutes").nullable()
    val genres: Column<String> = varchar("genres", 50) // TODO: Own Table?
}

object Ratings : IntIdTable(){
    val tconst : Column<String> = varchar("tconst", 10).uniqueIndex()
    val averageRating : Column<Float?> = float("averageRating").nullable()
    val numVotes : Column<Int?> = integer("numVotes").nullable()
}

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
```


## Author

* [Julien Lengrand-Lambert](https://github.com/jlengrand/)
