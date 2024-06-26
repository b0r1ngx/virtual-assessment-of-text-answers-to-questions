import dev.boringx.Database

class ClientRepository(
    private val database: Database,
    private val api: ClientApi,
) : Repository(database = database) {

    fun getAppUser(): UserModel? {
        with(database) {
            // on clients, first User entry equals to user that registered on this client
            val user = userQueries.selectAllById(id = 1).executeAsOneOrNull() ?: return null

            val userCoursesIds =
                userCoursesQueries.selectCoursesIds(user_id = user.id).executeAsList()

            val userCourses = courseQueries.selectAllIn(
                id = userCoursesIds, mapper = { id, name -> Course(id, name) }
            ).executeAsList()

            return UserModel(
                typeId = user.user_type_id.toInt(),
                name = user.name,
                email = user.email,
                courses = userCourses
            )
        }
    }

    override suspend fun createTest(test: TestModel) {
        super.createTest(test)
        api.createTest(test)
    }

    override suspend fun getTests(): List<TestModel> {
        val localTests = super.getTests()
        val remoteTests = api.getTests()
        remoteTests.forEach { test ->
            if (!isTestAlreadyExistsLocally(test, localTests)) {
                super.createTest(test)
            }
        }
        return remoteTests.ifEmpty { localTests }
    }

    private fun isTestAlreadyExistsLocally(test: TestModel, localTests: List<TestModel>): Boolean =
        localTests.any {
            test.creator == it.creator
                    && test.course.name == it.course.name
                    && test.name == it.name
                    && test.questions.size == it.questions.size
                    && test.questions == it.questions
        }

    override suspend fun createUser(user: UserModel) {
        super.createUser(user)
        api.registerUser(user)
    }

    suspend fun saveAnswers(testAnswers: TestAnswers, isTestCompleted: Boolean) {
        super.saveAnswers(testAnswers)
        if (isTestCompleted) api.saveAnswers(testAnswers)
    }

    override suspend fun getAnswers(testId: Long): List<TestAnswers> {
        val localAnswers = super.getAnswers(testId)
        val remoteAnswers = api.getAnswers(testId)

        val fixedLocalAnswers = localAnswers.map { appendQuestionsToAnswers(it) }
        val fixedRemoteAnswers = remoteAnswers.map { appendQuestionsToAnswers(it) }

        fixedRemoteAnswers.forEach { testAnswers ->
            if (isAnswerAlreadyExistsLocally(testAnswers, fixedLocalAnswers)) {
                super.saveAnswers(testAnswers)
            }
        }
        return fixedRemoteAnswers.ifEmpty { fixedLocalAnswers }
    }

    private fun isAnswerAlreadyExistsLocally(
        answer: TestAnswers,
        localAnswers: List<TestAnswers>,
    ): Boolean =
        localAnswers.any {
            answer.user == it.user
                    && answer.questionsToAnswers == it.questionsToAnswers
        }


    override suspend fun saveFinalAssessment(assessment: Assessment) {
        // TODO: crashing, with not finding a student locally (thinks that we not saving students, when get all answers of students, but must to that)!
        //  above assumption is not true, recheck
//        super.saveFinalAssessment(assessment)
        api.saveFinalAssessment(assessment)
    }

    override suspend fun getFinalAssessmentToAssessedAnswers(
        testId: Long,
        studentEmail: String
    ): Pair<Assessment, TestAnswers>? {
        val localAssessments = super.getFinalAssessmentToAssessedAnswers(testId, studentEmail)
        val remoteAssessments = api.getFinalAssessmentToAssessedAnswers(testId, studentEmail)

        if (remoteAssessments == null) return null

        val testAnswers = appendQuestionsToAnswers(remoteAssessments.second)

        return remoteAssessments.first to testAnswers
    }

    private fun appendQuestionsToAnswers(testAnswers: TestAnswers): TestAnswers {
        val testQuestions = super.getQuestion(testId = testAnswers.testId)
        val answers = testAnswers.questionsToAnswers.map { (_, answer) -> answer }
        return testAnswers.copy(questionsToAnswers = testQuestions.zip(answers))
    }

}
