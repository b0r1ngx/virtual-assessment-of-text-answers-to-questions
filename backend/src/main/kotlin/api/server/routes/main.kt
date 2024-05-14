package api.server.routes

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import io.ktor.server.routing.get

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}