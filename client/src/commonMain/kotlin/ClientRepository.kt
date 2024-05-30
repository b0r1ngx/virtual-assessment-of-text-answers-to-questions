import dev.boringx.Database

class ClientRepository(
    database: Database,
    private val api: ClientApi,
) : Repository(database = database) {

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
