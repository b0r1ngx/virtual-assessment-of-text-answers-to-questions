package screens.auth

import Course
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import client.ClientRepository
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import model.UserType
import kotlin.coroutines.CoroutineContext

class AuthViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
) : ComponentContext by componentContext {

    // The scope is automatically cancelled when the component is destroyed
    private val scope = coroutineScope(mainCoroutineContext + SupervisorJob())

    // TODO: use User DTO
    var userType = mutableStateOf(UserType.Student)
    var name by mutableStateOf("")
    var email by mutableStateOf("")

    private var _courses = repository.getCourses()
    val courses: List<Course> = _courses

    val pickedCourses = mutableListOf<Course>()

    var isAllowedToRegister by mutableStateOf(false)
    fun validateRegistration() {
        isAllowedToRegister = name.isNotBlank() && email.isNotBlank() && pickedCourses.isNotEmpty()
    }

    fun registerUser() {
//        repository.createUser(
//            user = User(
//                type = userType.value.ordinal,
//                name = name,
//                email = email
//            )
//        )
    }
}