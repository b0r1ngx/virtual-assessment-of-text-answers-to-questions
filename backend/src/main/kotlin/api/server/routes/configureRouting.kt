package api.server.routes

import Repository
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting(repository: Repository) {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        get("/tests") {
            val tests = repository.getTests()
            call.respond(tests)
        }
    }
}