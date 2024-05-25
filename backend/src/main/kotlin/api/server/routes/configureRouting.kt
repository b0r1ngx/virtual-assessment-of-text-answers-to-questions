package api.server.routes

import Endpoints
import Repository
import TestAnswers
import TestModel
import User
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

fun Application.configureRouting(repository: Repository) {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
    userRoutes(repository)
    coursesRoutes(repository)
    testRoutes(repository)
    answerRoutes(repository)
}

fun Application.userRoutes(repository: Repository) {
    routing {
        route(Endpoints.user.path) {
            put {
                val user = call.receive<User>()
                repository.createUser(user = user)
                call.respond(HttpStatusCode.Created)
            }
        }
    }
}

fun Application.coursesRoutes(repository: Repository) {
    routing {
        route(Endpoints.course.path) {
            get {
                val courses = repository.getCourses()
                call.respond(courses)
            }
        }
    }
}

fun Application.testRoutes(repository: Repository) {
    routing {
        route(Endpoints.test.path) {
            get {
                val tests = repository.getTests()
                call.respond(tests)
            }

            put {
                val test = call.receive<TestModel>()
                repository.createTest(test)
                call.respond(HttpStatusCode.Created)
            }
        }
    }
}

fun Application.answerRoutes(repository: Repository) {
    routing {
        route(Endpoints.answer.path) {
            put {
                val testAnswers = call.receive<TestAnswers>()
                repository.saveAnswers(testAnswers)
                call.respond(HttpStatusCode.Created)
            }
            // add it to queue of the answers that must be assessed for virtual system
        }
    }
}
