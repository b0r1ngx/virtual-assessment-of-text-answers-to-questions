package api.server.routes

import Endpoints
import Repository
import TestAnswers
import TestModel
import UserModel
import core.assessAnswerAndSaveToDatabase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureRouting(repository: Repository) {
    routing {
        helloRoute()
        userRoutes(repository)
        courseRoutes(repository)
        testRoutes(repository)
    }
}

private fun Route.helloRoute() {
    route("/") {
        get {
            call.respondText("Hello, world!")
        }
    }
}

private fun Route.userRoutes(repository: Repository) {
    route(Endpoints.user.path) {
        put {
            val user = call.receive<UserModel>()
            println("user: $user")
            repository.createUser(user = user)
            call.respond(HttpStatusCode.Created)
        }
    }
}

private fun Route.courseRoutes(repository: Repository) {
    route(Endpoints.course.path) {
        get {
            val courses = repository.getCourses()
            call.respond(courses)
        }
    }
}

private fun Route.testRoutes(repository: Repository) {
    route(Endpoints.test.path) {
        get {
            // TODO: filter to get tests only appropriate for user
            val tests = repository.getTests()
            call.respond(tests)
        }

        put {
            val test = call.receive<TestModel>()
            repository.createTest(test)
            call.respond(HttpStatusCode.Created)
        }

        answerRoutes(repository)
    }
}

private fun Route.answerRoutes(repository: Repository) {
    route(Endpoints.answer.path) {
        get("/{testId}") {
            val badRequest: suspend () -> Unit = {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Wrong testId, must be a Long number"
                )
            }
            val testId = call.parameters["testId"]?.toLongOrNull() ?: return@get badRequest.invoke()
            val studentAnswers = repository.getAnswers(testId)
            call.respond(studentAnswers)
        }

        put {
            val testAnswers = call.receive<TestAnswers>()
            repository.saveAnswers(testAnswers)
            call.respond(HttpStatusCode.Created)
            assessAnswerAndSaveToDatabase(repository, testAnswers)
        }
    }
}
