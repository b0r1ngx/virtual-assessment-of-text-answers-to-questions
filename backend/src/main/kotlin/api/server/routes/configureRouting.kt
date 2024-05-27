package api.server.routes

import Endpoints
import Repository
import TestAnswers
import TestModel
import User
import core.assess
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
        answerRoutes(repository)
    }
}

fun Route.helloRoute() {
    route("/") {
        get {
            call.respondText("Hello, world!")
        }
    }
}

fun Route.userRoutes(repository: Repository) {
    route(Endpoints.user.path) {
        put {
            val user = call.receive<User>()
            repository.createUser(user = user)
            call.respond(HttpStatusCode.Created)
        }
    }
}

fun Route.courseRoutes(repository: Repository) {
    route(Endpoints.course.path) {
        get {
            val courses = repository.getCourses()
            call.respond(courses)
        }
    }
}

fun Route.testRoutes(repository: Repository) {
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

fun Route.answerRoutes(repository: Repository) {
    route(Endpoints.answer.path) {
        put {
            val testAnswers = call.receive<TestAnswers>()
            repository.saveAnswers(testAnswers)
            call.respond(HttpStatusCode.Created)
            assess(repository, testAnswers)
        }
    }
}
