package screens.test

import TestModel
import User
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import client.ClientRepository
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EditingTestViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    test: TestModel? = null, // if null -> teacher is creating new test, if not he is editing
    val onFinished: () -> Unit,
) : ComponentContext by componentContext {

    private val scope = coroutineScope(mainCoroutineContext + SupervisorJob())

    var name by mutableStateOf(test?.name ?: "")
    var course by mutableStateOf(test?.course)
    var start_at by mutableStateOf(test?.start_at)
    var end_at by mutableStateOf(test?.end_at)
    val questions = mutableStateOf(test?.questions)

    // TODO: it may be creating or editing a test, solve it at repository layer?
    //  check if test already exists, update values, instead of insert new
    fun saveTest(user: User) {
        scope.launch {
            repository.createTest(
                test = TestModel(
                    creator = user,
                    name = name,
                    course = course,
                    start_at = start_at,
                    end_at = end_at,
                    questions = questions
                )
            )
            // show Toast / Snackbar that test is saved successfully
        }
        onFinished()
    }

}
