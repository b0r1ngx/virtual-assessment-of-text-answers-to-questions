package client

import Repository
import TestModel
import User
import client.network.ClientApi
import dev.boringx.Database

class ClientRepository(
    private val database: Database,
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
            val firstLevelSameTests = localTests.filter {
                test.creator == it.creator
                        && test.course.name == it.course.name
                        && test.name == it.name
            }

            val secondLevelSameTests = firstLevelSameTests.filter {
                test.questions.size == it.questions.size
                        && test.questions == it.questions
            }

            if (secondLevelSameTests.isEmpty()) super.createTest(test)
        }
        return remoteTests.ifEmpty { localTests }
    }

    override suspend fun createUser(user: User) {
        super.createUser(user)
        api.registerUser(user)
        // TODO: if there was no internet, mark it by someway (to later, with internet, end with registering user on server)
    }
}
