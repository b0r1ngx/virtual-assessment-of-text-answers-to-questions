package screens.auth

import Course
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import client.Repository
import model.UserType

class AuthViewModel(
    private val repository: Repository,
) {
    var userType = mutableStateOf(UserType.Student)
    var name by mutableStateOf("")
    var email by mutableStateOf("")

    private var _courses = repository.getCourses()
    val courses: List<Course> = _courses

    val pickedCourses = mutableListOf<Course>()

}