package screens.test

import Test
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TestViewModel(val test: Test) {
    var testName by mutableStateOf("")

    fun saveTest() {
        TODO("Not yet implemented")
    }

}
