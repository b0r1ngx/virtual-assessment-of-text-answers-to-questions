package api.server

import Repository
import SqlDriverFactory
import api.server.routes.configureRouting
import createDatabase
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    val repository = Repository(createDatabase(SqlDriverFactory()))
    configureRouting(repository)
}