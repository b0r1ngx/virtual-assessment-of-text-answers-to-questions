package screens.test

import TestModel
import User
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import client.ClientRepository
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import screens.utils.toHours
import kotlin.coroutines.CoroutineContext

class EditingTestViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    test: TestModel? = null, // if null -> teacher is creating new test, if not he is editing
    val onFinished: () -> Unit,
) : ComponentContext by componentContext {

    private val scope = coroutineScope(mainCoroutineContext + SupervisorJob())

    var name by mutableStateOf(
        TextFieldValue(
            text = test?.name ?: "",
            selection = TextRange((test?.name ?: "").length)
        )
    )
    var course by mutableStateOf(test?.course)
    var startAt = mutableStateOf(test?.start_at ?: Clock.System.now())
    var endAt = mutableStateOf(test?.end_at ?: Clock.System.now().plus(1.toHours()))
    val questions = mutableStateOf(test?.questions)

    fun validateTestCreation() {
        val now = Clock.System.now()
        name.text.isNotBlank() &&
                course != null &&
                startAt.value >= now &&
                endAt.value >= startAt.value &&
                questions.value != null && questions.value?.isNotEmpty() ?: false
    }

    fun saveDateAndTime(at: MutableState<Instant>, dateMillis: Long, hour: Int, minute: Int) {
        at.value = Instant.fromEpochMilliseconds(
            epochMilliseconds = dateMillis + (((hour * 60) + minute) * 60) * 1000
        )
    }

    // TODO: it may be creating or editing a test, solve it at repository layer?
    //  check if test already exists, update values, instead of insert new
    fun saveTest(user: User) {
        scope.launch {
            repository.createTest(
                test = TestModel(
                    creator = user,
                    name = name.text,
                    course = course!!,
                    start_at = startAt.value!!,
                    end_at = endAt.value!!,
                    questions = questions.value!!
                )
            )
            // show Toast / Snackbar that test is saved successfully
        }
        onFinished()
    }

}
