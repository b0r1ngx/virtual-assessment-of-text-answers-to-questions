import dev.boringx.Database
import dev.boringx.User
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
                .selectAllById(
                    id = test.creator_id,
                    mapper = { _, userTypeId, name, email ->
                        UserModel(typeId = userTypeId.toInt(), name = name, email = email)
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
    // currently, teacher must not have ability to edit already created tests
    open suspend fun createTest(test: TestModel) {
        val creator = getUserAndInsertIfNotExist(test.creator)
        with(database) {
            transaction {
                val newTestId: Long
                with(testQueries) {
                    insert(
                        creator_id = creator.id,
                        course_id = test.course.id,
                        name = test.name,
                        start_at = test.start_at.toString(),
                        end_at = test.end_at.toString(),
                        created_at = Clock.System.now().toString()
                    )
                    newTestId = lastInsertRowId().executeAsOne()
                }

                test.questions.forEach {
                    val newQuestionId: Long
                    with(questionQueries) {
                        insert(text = it.text)
                        newQuestionId = lastInsertRowId().executeAsOne()
                    }

                    testQuestionsQueries.insert(test_id = newTestId, question_id = newQuestionId)
                }
            }
        }
    }

    // wrapping individual inserts in transaction is faster.
    // source: https://stackoverflow.com/a/5009740/13432944
    // TODO: test, w/o transaction, test insert multiple values, not individual
    //  check: https://stackoverflow.com/questions/1609637/how-to-insert-multiple-rows-in-sqlite
    suspend fun saveAnswers(testAnswers: TestAnswers): List<Answer> {
        val answersWithIds = mutableListOf<Answer>()

        val student = getUserAndInsertIfNotExist(testAnswers.user)
        with(database) {
            transaction {
                testAnswers.questionsToAnswers.forEach { (question, answer) ->
                    answerQueries.insert(
                        test_id = testAnswers.testId,
                        student_id = student.id,
                        question_id = question.id,
                        text = answer.text
                    )

                    answersWithIds.add(
                        answer.copy(id = answerQueries.lastInsertRowId().executeAsOne())
                    )
                }

                testPassedQueries.insert(
                    test_id = testAnswers.testId,
                    student_id = student.id
                )
            }
        }

        return answersWithIds
    }

    open suspend fun getAnswers(testId: Long): List<TestAnswers> {
        val testAnswers = mutableListOf<TestAnswers>()

        with(database) {
            val testPasses = testPassedQueries.selectAllBy(test_id = testId).executeAsList()
            val studentIdsOfTest = testPasses.map { it.student_id }

            val students = userQueries.selectAllIn(id = studentIdsOfTest).executeAsList()

            students.forEach {
                val userModel =
                    UserModel(typeId = it.user_type_id.toInt(), name = it.name, email = it.email)

                val studentAnswers = answerQueries.selectAllBy(
                    test_id = testId,
                    student_id = it.id,
                    mapper = { id, text, avgMarkAi, _, _, _ ->
                        // creating mock question here, maybe question doesn't need for TestAnswers?
                        Question(text = "") to Answer(
                            id = id,
                            text = text,
                            avgMarkAi = avgMarkAi ?: -1.0
                        )
                    }
                ).executeAsList()

                testAnswers.add(
                    TestAnswers(
                        testId = testId,
                        user = userModel,
                        questionsToAnswers = studentAnswers
                    )
                )
            }
        }

        return testAnswers
    }

    // suspending is only required by ClientRepository, because there is API call to server
    // but why databases interaction via SQLDelight are not async?
    open suspend fun createUser(user: UserModel) {
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

    private suspend fun getUserAndInsertIfNotExist(user: UserModel): User {
        val foundedUser = database.userQueries.getUser(user.email)

        if (foundedUser == null) createUser(user)
        else return foundedUser

        return database.userQueries.selectAllByEmail(user.email).executeAsOne()
    }

    private fun UserQueries.getUser(email: String) =
        selectAllByEmail(email).executeAsOneOrNull()

    suspend fun saveAssessment(
        testAnswers: TestAnswers,
        testAssessments: List<TestAssessments>
    ) {
        val student = getUserAndInsertIfNotExist(testAnswers.user)
        with(database) {
            transaction {
                val aiMarks = mutableListOf<Double>()
                testAssessments.forEach { assessment ->
                    assessment.criterionToMarkWithResponse.forEach { (criterion, mark, response) ->
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

                    aiMarks.add(assessment.avgMarkAi)
                    answerQueries.updateAvgMarkAi(
                        avg_mark_ai = assessment.avgMarkAi,
                        id = assessment.answer.id
                    )
                }

                testPassedQueries.updateAvgMarkAi(
                    avg_mark_ai = aiMarks.average(),
                    test_id = testAnswers.testId,
                    student_id = student.id
                )
            }
        }
    }

    fun checkAssessments() {
        with(database) {
            val answers = answerQueries.selectAll().executeAsList()
            val assessments = assessmentQueries.selectAll().executeAsList()
            val answersToAssessments = answerAssessmentQueries.selectAll().executeAsList()
            println("Answers: $answers")
            println("Assessments: $assessments")
            println("AnswersToAssessments: $answersToAssessments")
        }
    }
}
