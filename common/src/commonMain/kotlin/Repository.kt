import dev.boringx.Database
import dev.boringx.UserQueries
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

open class Repository(private val database: Database) {
    // TODO: Later, also make request to the API from ClientRepository
    fun getCourses(): List<Course> {
        return database.courseQueries
            .selectAll { id, name -> Course(id = id, name = name) }
            .executeAsList()
    }

    // todo: use join in one query, instead tons of queries
    //  (but how to then know what the data is out, is sqldelight handle this?
    open suspend fun getTests(): List<TestModel> {
        val tests = database.testQueries.selectAll().executeAsList()
        val results: MutableList<TestModel> = mutableListOf()
        tests.forEach { test ->
            val user = database.userQueries
                .selectAll(
                    id = test.id,
                    mapper = { _, userTypeId, name, email ->
                        User(typeId = userTypeId.toInt(), name = name, email = email)
                    }
                )
                .executeAsOne()

            val course = database.courseQueries
                .selectAllBy(
                    id = test.course_id,
                    mapper = { id, name -> Course(id, name) }
                )
                .executeAsOne()

            val testQuestionsIds = database.testQuestionsQueries
                .selectQuestionsIds(test_id = test.id)
                .executeAsList()

            val questions = database.questionQueries
                .selectAllIn(id = testQuestionsIds, mapper = { id, text -> Question(id, text) })
                .executeAsList()

            results.add(
                TestModel(
                    id = test.id,
                    creator = user,
                    name = test.name,
                    course = course,
                    start_at = Instant.parse(test.start_at),
                    end_at = Instant.parse(test.end_at),
                    questions = questions
                )
            )
        }
        return results
    }

    // todo: we must make this operation upsert
    open suspend fun createTest(test: TestModel) {
        return database.testQueries.insert(
            creator_id = database.userQueries.getUser(test.creator.email).id,
            course_id = test.course.id,
            name = test.name,
            start_at = test.start_at.toString(),
            end_at = test.end_at.toString(),
            created_at = Clock.System.now().toString()
        )
    }

    // wrapping individual inserts in transaction is faster.
    // source: https://stackoverflow.com/a/5009740/13432944
    // TODO: test, w/o transaction, test insert multiple values, not individual
    //  check: https://stackoverflow.com/questions/1609637/how-to-insert-multiple-rows-in-sqlite
    fun saveAnswers(testAnswers: TestAnswers): List<Answer> {
        val answersWithIds = mutableListOf<Answer>()

        with(testAnswers) {
            with(database) {
                transaction {
                    questionsToAnswers.forEach { (question, answer) ->
                        answerQueries.insert(
                            test_id = testId,
                            student_id = userQueries.getUser(userEmail).id,
                            question_id = question.id,
                            text = answer.text
                        )

                        answersWithIds.add(
                            answer.copy(id = answerQueries.lastInsertRowId().executeAsOne())
                        )
                    }
                }
            }
        }

        return answersWithIds
    }

    // suspending is only required by ClientRepository, because there is API call to server
    // but why databases interaction via SQLDelight are not async?
    open suspend fun createUser(user: User) {
        with(database) {
            transaction {
                val newUserId: Long
                with(userQueries) {
                    insert(
                        user_type_id = user.typeId.toLong(),
                        name = user.name,
                        email = user.email
                    )
                    newUserId = lastInsertRowId().executeAsOne()
                }
                user.courses.forEach { course ->
                    userCoursesQueries.insert(user_id = newUserId, course_id = course.id)
                }
            }
        }
    }

    private fun UserQueries.getUser(email: String) =
        selectAllBy(email).executeAsOne()

    fun saveAssessment(testAssessments: List<TestAssessments>) {
        with(database) {
            transaction {
                testAssessments.forEach { assessment ->
                    assessment.criteriaToMarkWithResponse.forEach { (criterion, mark, response) ->
                        val newAssessmentId: Long
                        with(assessmentQueries) {
                            insert(
                                criterion_id = criterion.ordinal.toLong(),
                                mark = mark.toLong(),
                                raw_response = response
                            )
                            newAssessmentId = lastInsertRowId().executeAsOne()
                        }
                        answerAssessmentQueries.insert(
                            answer_id = assessment.answer.id,
                            assessment_id = newAssessmentId
                        )
                    }

                    answerQueries.updateAvgMarkAi(
                        avg_mark_ai = assessment.avgMarkAi,
                        id = assessment.answer.id
                    )
                }
            }
        }
    }
}
