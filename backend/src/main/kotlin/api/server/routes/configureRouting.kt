package api.server.routes

import Repository
import dev.boringx.Test
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import User

fun Application.configureRouting(repository: Repository) {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
    userRoutes(repository)
    testRoutes(repository)
}

fun Application.testRoutes(repository: Repository) {
    routing {
        route("/tests") {
            get {
                val tests = repository.getTests()
                call.respond(tests)
            }

            put {
                val test = call.receive<Test>()
                repository.createTest(test)
                call.respond(HttpStatusCode.Created)
            }
        }
    }
}

fun Application.userRoutes(repository: Repository) {
    routing {
        route("/user") {
            put {
                val user = call.receive<User>()
                repository.createUser(user = user)
                call.respond(HttpStatusCode.Created)
            }
        }
    }
}