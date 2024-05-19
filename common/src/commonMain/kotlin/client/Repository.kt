package client

import Course
import Test
import client.network.ClientApi
import dev.boringx.Database

class Repository(
    private val database: Database,
    private val api: ClientApi,
) {

    fun getCourses(): List<Course> {
        return database.courseQueries
            .selectAll { id, name -> Course(name = name ?: "") }
            .executeAsList()
    }

    suspend fun getTests(): List<Test> {
        val localTests = database.testQueries.selectAll().executeAsList()
        // if user has internet also execute this V
        val remoteTests = api.getTests()

        // update local database with new remote entries
        remoteTests.forEach {
            database.testQueries
        }
        return remoteTests
    }
}