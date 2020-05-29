package nl.lengrand.imdb.api

import io.vertx.core.Vertx

fun main(){
    Vertx.vertx().createHttpServer()
        .requestHandler { request ->
            request.response().end("Imdb Server")
        }.listen(8080)
}