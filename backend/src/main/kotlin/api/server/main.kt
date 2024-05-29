package api.server

import LOCAL_SERVER_IP
import LOCAL_SERVER_PORT
import Repository
import SqlDriverFactory
import api.server.routes.configureRouting
import createDatabase
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(
        factory = Netty,
        port = LOCAL_SERVER_PORT,
        host = LOCAL_SERVER_IP,
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(CallLogging)
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    val repository = Repository(createDatabase(SqlDriverFactory()))
    configureRouting(repository)
}