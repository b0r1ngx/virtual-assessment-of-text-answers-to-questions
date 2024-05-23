package screens.test

import Test
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import client.ClientRepository
import com.arkivanov.decompose.ComponentContext
import kotlin.coroutines.CoroutineContext

class EditingTestViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    test: Test? = null, // if null -> teacher is creating new test, if not he is editing
    val onFinished: () -> Unit,
) : ComponentContext by componentContext {

    var name by mutableStateOf(test?.name ?: "")
    var course by mutableStateOf(test?.course)
    var start_at by mutableStateOf(test?.start_at)
    var end_at by mutableStateOf(test?.end_at)
    val questions = mutableStateOf(test?.questions)

    fun saveTest() {
        // TODO("Not yet implemented")
//        repository.createTest(
//            test = Test(
//                creator = ,// currentUser
//                name = name,
//                course = Course(name = course),
//
//            )
//        )
        onFinished()
        // show Toast / Snackbar that test is saved successfully
    }

}
