package screens.test

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.complete_test
import org.jetbrains.compose.resources.stringResource

@Composable
fun PassingTestScreen(
    testViewModel: PassingTestViewModel
) {
    // info about test with questions

    // if testViewModel.testInProgress draw one things, else draw info about test
    // EditingTest.info, like at card
    LazyColumn {
//        items(testViewModel.test.questions) {
//
//        }
    }
    Button(onClick = { }) {
        Text(stringResource(Res.string.complete_test))
    }
    // progressBar?
    // Text(text= "currentQuestion / questions.size"

    // question.text

    // TextField to input answer

    // if currentQuestion == questions.last
    //      Finish Button
    // else
    //      Button for navigate to next question
    //        (disable this button, if TextField.value.isEmpty)
}