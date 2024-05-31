import dev.boringx.Database

class ClientRepository(
    private val database: Database,
    private val api: ClientApi,
) : Repository(database = database) {

    fun getUserSelf(): UserModel? {
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
        // map modelTest to TestModel DB Entity
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

}
