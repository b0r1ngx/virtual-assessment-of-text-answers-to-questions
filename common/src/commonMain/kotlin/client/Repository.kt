package client

import client.network.ClientApi
import dev.boringx.Database
import dev.boringx.Test
import Repository
import users.User

class Repository(
    private val database: Database,
    private val api: ClientApi,
) : Repository(database = database) {

    override suspend fun getTests(): List<Test> {
        val localTests = super.getTests()
        val remoteTests = api.getTests()
        remoteTests.forEach {
            database.testQueries.insert(
                creator_id = it.creator_id,
                course_id = it.course_id,
                name = it.name,
                start_at = it.start_at,
                end_at = it.end_at,
                created_at = it.created_at
            )
        }
        return remoteTests.ifEmpty { localTests }
    }

    override suspend fun createUser(user: User) {
        super.createUser(user)
        api.registerUser(user)
        // TODO: if there was no internet, mark it by someway (to later, with internet, end with registering user)
    }
}