package api.server.routes

import Course
import Endpoints
import Question
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
import kotlinx.datetime.Clock
import kotlin.time.DurationUnit
import kotlin.time.toDuration

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
            val user = call.receive<UserModel>()
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
private val mockTelecomQuestions = listOf(
    Question(text = "Как происходит увеличение числа операций умножения с ростом порядка рекурсивного фильтра"),
    Question(text = "Что такое медианный фильтр? Какие у него свойства?"),
)

private val mockArchitectureQuestions = listOf(
    Question(text = "Общие сведения об управлении потребляемой мощностью в микроконтроллере"),
    Question(text = "Основные классификационные признаки микропроцессорной системы"),
)

private val mockOSBQuestions = listOf(
    Question(text = "Продолжите фразу. Операционная система – это …"), // виртуальная машина
    Question(text = "Продолжите фразу. Операционная система – это  …"), // реактивная система
    Question(text = "Продолжите фразу. Операционная система – это система управления …"), // ресурсами
    Question(text = "Продолжите фразу. Процесс в операционной системе – это …"), // выполняющаяся программа
    Question(text = "Какой атрибут файла в операционной системе является основным ?"), // имя
    Question(text = "Какой атрибут процесса в операционной системе является основным ?"), // идентификатор
    Question(text = "Какой тип отношения между управляемыми объектами операционной системы формирует пользователь операционной системы ?"), // Процесс – Ресурс
    Question(text = "Какое ядро имеет операционная система Microsoft Windows 10 ?"), // Microsoft Windows NT
    Question(text = "Какое ядро имеет операционная система Ubuntu 16.04 LTS Server ?"), // Linux
    Question(text = "Что происходит при выгрузке операционной системы из оперативной памяти компьютера ?"), // Закрытие (с сохранением вне оперативной памяти) всех открытых файлов и завершение всех выполняющихся процессов операционной системы
    Question(text = "Перечислите компоненты, которые входят в состав автоматизированной системы решения прикладных задач на компьютере ?"), // Операционная система, Пользователь, Вычислительная машина, Системная программа, Прикладная программа
    Question(text = "На каких этапах жизненного цикла процесса операционной системы ему требуется сегмент стека ?"), // Только на этапах возникновения прерываний выполнения процесса
)

private val mockTests = listOf(
    TestModel(
        id = 1,
        creator = UserModel(
            typeId = 2,
            name = "Лупин Анатолий Викторович",
            email = "lupin.av@edu.spbstu.ru"
        ),
        name = "Промежуточное тестирование",
        course = Course(id = 1, name = "Цифровая обработка сигналов"),
        start_at = Clock.System.now().minus(2.toDuration(DurationUnit.HOURS)),
        end_at = Clock.System.now().minus(1.toDuration(DurationUnit.HOURS)),
        questions = mockTelecomQuestions
    ),
    TestModel(
        id = 2,
        creator = UserModel(
            typeId = 2,
            name = "Богач Наталья Владимировна",
            email = "bogach.nv@edu.spbstu.ru"
        ),
        name = "Промежуточное тестирование",
        course = Course(id = 2, name = "Телекоммуникационные технологии"),
        start_at = Clock.System.now(),
        end_at = Clock.System.now().plus(1.toDuration(DurationUnit.HOURS)),
        questions = mockTelecomQuestions
    ),
    TestModel(
        id = 3,
        creator = UserModel(
            typeId = 2,
            name = "Тарасов Олег Михайлович",
            email = "tarasov.om@edu.spbstu.ru"
        ),
        name = "Экзамен",
        course = Course(id = 3, name = "Архитектура ЭВМ"),
        start_at = Clock.System.now(),
        end_at = Clock.System.now().plus(2.toDuration(DurationUnit.HOURS)),
        questions = mockArchitectureQuestions
    ),
    TestModel(
        creator = UserModel(
            typeId = 2,
            name = "Малышев Игорь Алексеевич",
            email = "malyshev.ia@edu.spbstu.ru"
        ),
        name = "Итоговое тестирование",
        course = Course(id = 4, name = "Основы операционных систем"),
        start_at = Clock.System.now(),
        end_at = Clock.System.now().plus(1.toDuration(DurationUnit.DAYS)),
        questions = mockOSBQuestions
    ),
)

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
            assessAnswerAndSaveToDatabase(repository, testAnswers)
        }
    }
}
