import dev.boringx.Database
import dev.boringx.Test
import dev.boringx.TestQuestions

open class Repository(private val database: Database) {
    // TODO: Later, also make request to the API from ClientRepository
    fun getCourses(): List<Course> {
        return database.courseQueries
            .selectAll { id, name -> Course(id = id, name = name) }
            .executeAsList()
    }

    open suspend fun getTests(): List<Test> {
        // todo: use join in one query, instead tons of queries
        val tests = database.testQueries.selectAll().executeAsList()
        val testQuestions = mutableListOf<TestQuestions>()
        tests.forEach { test ->
//            testQuestions.add(
//                database.testQuestionsQueries
//                    .selectBy(test_id = test.id)
//                    .executeAsList()
//            )
        }
        val questions = tests.forEach {

        }
        return tests
    }

    open suspend fun createTest(test: Test) {
        return database.testQueries.insert(
            creator_id = test.creator_id,
            course_id = test.course_id,
            name = test.name,
            start_at = test.start_at,
            end_at = test.end_at,
            created_at = test.created_at
        )
    }

    open suspend fun addAnswers(
        student: User,
        test: Test,
        answers: Map<Question, Answer>
    ) {
        answers.forEach { (question, answer) ->
//            database.answerQueries.insert(
//                test_id = test.id,
//                question_id = question.id,
//                student_id = student.id,
//                text = answer.text
//            )
        }
    }

    // suspending is only required by ClientRepository, because there is API call to server
    // but why databases interaction via SQLDelight are not async?
    open suspend fun createUser(user: User) {
        with(database) {
            transaction {
                val newUserId: Long
                with(userQueries) {
                    insert(user_type_id = user.type.toLong(), name = user.name, email = user.email)
                    newUserId = lastInsertRowId().executeAsOne()
                }
                user.courses.forEach { course ->
                    userCoursesQueries.insert(user_id = newUserId, course_id = course.id)
                }
            }
        }
    }

}
