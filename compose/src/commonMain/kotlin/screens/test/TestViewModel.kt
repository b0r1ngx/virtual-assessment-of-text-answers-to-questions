package screens.test

import Test
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import kotlin.coroutines.CoroutineContext

class TestViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    val test: Test,
) : ComponentContext by componentContext {
    var testName by mutableStateOf("")

    fun saveTest() {
        TODO("Not yet implemented")
    }

}
