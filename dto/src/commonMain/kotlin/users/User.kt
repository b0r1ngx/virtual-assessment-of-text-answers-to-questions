package users

import Course
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val type: Int, // UserType
    val name: String,
    val email: String,
    // TODO: later, delete default value? because in real app workflow, it is not possible to user has zero courses,
    //  but for tests its may be allowed (when user pick 0 courses on AuthScreen)
    val courses: List<Course> = listOf()
)
