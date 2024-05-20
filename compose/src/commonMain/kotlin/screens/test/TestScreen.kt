package screens.test

import Question
import UserViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.add_question_button
import dev.boringx.compose.generated.resources.complete_test
import dev.boringx.compose.generated.resources.save_test_button
import model.UserType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TestScreen(
    userViewModel: UserViewModel,
    testViewModel: TestViewModel
) {
    if (userViewModel.user?.type == UserType.Student.ordinal) {
        // info about test with questions

        // if testViewModel.testInProgress draw one things, else draw info about test
        Column {
            // Test.info, like at card
            LazyColumn {
                items(testViewModel.test.questions) {

                }
            }
            Button(onClick = { }) {
                Text(stringResource(Res.string.complete_test))
            }
        }

        Column {
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
    } else {
        // edit test

        Button(onClick = {
            // TODO: Allow to add question via dialog?/ navigate to other screen
        }) {
            Text(text = stringResource(Res.string.add_question_button))
        }

        if (testViewModel.test.questions.isNotEmpty()) {
            Button(onClick = {
                testViewModel.saveTest()
            }) {
                Text(text = stringResource(Res.string.save_test_button))
            }
        }
    }
}

@Composable
fun Question(question: Question) {
    Text(text = question.text)
}
