import Course
import dev.boringx.Database
import dev.boringx.Test
import users.User

abstract class Repository(private val database: Database) {
    fun getCourses(): List<Course> {
        return database.courseQueries
            .selectAll { id, name -> Course(name = name) }
            .executeAsList()
    }

    open suspend fun getTests(): List<Test> {
        return database.testQueries.selectAll().executeAsList()
    }

    open suspend fun createUser(user: User) {
        database.userQueries.insert(
            user_type_id = user.type.toLong(),
            name = user.name,
            email = user.email
        )
    }
}
