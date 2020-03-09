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

## Author

* [Julien Lengrand-Lambert](https://github.com/jlengrand/)
