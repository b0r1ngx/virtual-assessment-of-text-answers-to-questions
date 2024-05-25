package client

import Repository
import TestAnswers
import TestModel
import User
import client.network.ClientApi
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
            if (isTestAlreadyExistsLocally(test, localTests)) super.createTest(test)
        }
        return remoteTests.ifEmpty { localTests }
    }

    private fun isTestAlreadyExistsLocally(test: TestModel, at: List<TestModel>): Boolean {
        val firstLevelSameTests = at.filter {
            test.creator == it.creator
                    && test.course.name == it.course.name
                    && test.name == it.name
        }

        val secondLevelSameTests = firstLevelSameTests.filter {
            test.questions.size == it.questions.size
                    && test.questions == it.questions
        }

        return secondLevelSameTests.isNotEmpty()
    }

    override suspend fun createUser(user: User) {
        super.createUser(user)
        api.registerUser(user)
        // TODO: if there was no internet, mark it by someway (to later, with internet, end with registering user on server)
    }

    suspend fun saveAnswers(testAnswers: TestAnswers, isTestCompleted: Boolean) {
        super.saveAnswers(testAnswers)
        if (isTestCompleted) api.saveAnswers(testAnswers)
    }

}
