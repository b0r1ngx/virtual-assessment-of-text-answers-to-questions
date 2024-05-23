package screens.test

import Answer
import Question
import Test
import androidx.compose.runtime.mutableStateOf
import client.ClientRepository
import com.arkivanov.decompose.ComponentContext
import kotlin.coroutines.CoroutineContext

class PassingTestViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    test: Test,
    val onFinished: () -> Unit,
) : ComponentContext by componentContext {
    var answers =
        mutableStateOf(mutableListOf<Pair<Question, Answer>>())
}