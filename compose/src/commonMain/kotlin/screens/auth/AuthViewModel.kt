package screens.auth

import Course
import User
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import client.ClientRepository
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val _courses: MutableStateFlow<List<Course>> = MutableStateFlow(listOf())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    val pickedCourses = mutableListOf<Course>()

    var isAllowedToRegister by mutableStateOf(false)

    init {
        _courses.update { repository.getCourses() }
    }

    fun validateRegistration() {
        isAllowedToRegister = name.isNotBlank() && email.isNotBlank() && pickedCourses.isNotEmpty()
    }

    fun registerUser() {
        scope.launch {
            repository.createUser(
                user = User(
                    type = userType.value.ordinal,
                    name = name,
                    email = email,
                    courses = courses.value
                )
            )
        }
    }
}