package client

import Repository
import TestModel
import User
import client.network.ClientApi
import dev.boringx.Database
import dev.boringx.Test

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
            database.testQueries.insert(
                creator_id = test.creator_id,
                course_id = test.course_id,
                name = test.name,
                start_at = test.start_at.toString(),
                end_at = test.end_at.toString(),
                created_at = test.created_at
            )
        }
        return remoteTests.ifEmpty { localTests }
    }

    override suspend fun createUser(user: User) {
        super.createUser(user)
        api.registerUser(user)
        // TODO: if there was no internet, mark it by someway (to later, with internet, end with registering user on server)
    }
}
