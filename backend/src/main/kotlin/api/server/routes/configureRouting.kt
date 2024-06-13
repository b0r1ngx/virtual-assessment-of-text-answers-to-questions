package api.server.routes

import Assessment
import Endpoints
import Repository
import TestAnswers
import TestModel
import UserModel
import core.assessAnswerAndSaveToDatabase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
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
        assessRoutes(repository)
    }
}

private fun Route.answerRoutes(repository: Repository) {
    route(Endpoints.answer.path) {
        get("/{testId}") {
            val badRequest: suspend () -> Unit = {
                call.badRequest(message = "Wrong testId, must be a Long number")
            }
            val testId = call.parameters["testId"]?.toLongOrNull() ?: return@get badRequest.invoke()
            val studentAnswers = repository.getAnswers(testId)
            call.respond(studentAnswers)
        }

        put {
            val testAnswers = call.receive<TestAnswers>()
            val questionsToAnswersWithIds = repository.saveAnswers(testAnswers)

            call.respond(HttpStatusCode.Created)

            val testAnswersWithIds = testAnswers.copy(questionsToAnswers = questionsToAnswersWithIds)
            assessAnswerAndSaveToDatabase(repository, testAnswersWithIds)
        }
    }
}

private fun Route.assessRoutes(repository: Repository) {
    route(Endpoints.assess.path) {
        get {
            val badRequest: suspend () -> Unit = {
                call.badRequest(message = "Wrong parameters, testId must be a Long number, studentEmail must be an email")
            }
            val testId = call.parameters["testId"]?.toLongOrNull() ?: return@get badRequest.invoke()
            val studentEmail = call.parameters["studentEmail"] ?: return@get badRequest.invoke()

            val finalAssessmentToAnswers = repository.getFinalAssessmentToAssessedAnswers(
                testId = testId,
                studentEmail = studentEmail
            )
            if (finalAssessmentToAnswers == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(finalAssessmentToAnswers)
            }
        }

        put {
            val assessment = call.receive<Assessment>()
            repository.saveFinalAssessment(assessment)
            call.respond(HttpStatusCode.Created)
        }
    }
}

suspend fun ApplicationCall.badRequest(message: String) {
    respond(status = HttpStatusCode.BadRequest, message = message)
}
