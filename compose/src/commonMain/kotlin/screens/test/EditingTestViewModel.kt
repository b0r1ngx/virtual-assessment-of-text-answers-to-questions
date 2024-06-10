package screens.test

import ClientRepository
import Question
import TestModel
import UserModel
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import screens.utils.fromSystemTimeZoneToUTC
import screens.utils.toHours
import kotlin.coroutines.CoroutineContext

class EditingTestViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    val test: TestModel? = null, // if null -> teacher is creating new test, if not he is editing
    val onCreateTest: (test: TestModel) -> Unit,
    val onFinished: () -> Unit
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

    private val _questions = MutableStateFlow(test?.questions ?: listOf())
    val questions: StateFlow<List<Question>> = _questions

    fun validateTestCreation() {
        val now = Clock.System.now()
        name.text.isNotBlank() &&
                course != null &&
                startAt.value >= now &&
                endAt.value >= startAt.value &&
                questions.value.isNotEmpty()
    }

    fun saveDateAndTime(at: MutableState<Instant>, dateMillis: Long, hour: Int, minute: Int) {
        at.value = Instant.fromEpochMilliseconds(
            epochMilliseconds = dateMillis + (((hour * 60) + minute) * 60) * 1000
        )
    }

    fun upsertQuestion(text: String, index: Int?) {
        if (index == null) {
            _questions.update { it + Question(text = text) }
        } else {
            _questions.update {
                val mutable = it.toMutableList()
                mutable[index] = Question(text = text)
                mutable
            }
        }
    }

    // TODO: it may be creating or editing a test, solve it at repository layer?
    //  check if test already exists, update values, instead of insert new

    // TODO: For current version, lets not allow to edit test, once it created
    fun saveTest(user: UserModel) {
        val test = TestModel(
            creator = user,
            name = name.text,
            course = course!!,
            start_at = startAt.value.fromSystemTimeZoneToUTC(),
            end_at = endAt.value.fromSystemTimeZoneToUTC(),
            questions = questions.value
        )
        onCreateTest(test)
    }

}
