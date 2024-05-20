package screens.test

import Question
import UserViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.Subtitle
import components.Title
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.add_question_button
import dev.boringx.compose.generated.resources.complete_test
import dev.boringx.compose.generated.resources.save_test_button
import dev.boringx.compose.generated.resources.test
import dev.boringx.compose.generated.resources.test_creation
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import model.UserType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.utils.toHours
import screens.utils.toLocalDateTime

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    userViewModel: UserViewModel,
    testViewModel: TestViewModel
) {
    Column {
        Title(
            text = stringResource(Res.string.test),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
        )
        // create Enum TestScreenStatus
        Subtitle(
            text = stringResource(Res.string.test_creation),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
        )

        if (userViewModel.user?.type == UserType.Student.ordinal) {
            // info about test with questions

            // if testViewModel.testInProgress draw one things, else draw info about test
            // Test.info, like at card
            LazyColumn {
                items(testViewModel.test.questions) {

                }
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
        } else {
            // edit test
//        Column(
//            horizontalAlignment = Alignment.Start,
//            modifier = Modifier.padding(5.dp)
//        ) {
//            Text(text = test.name)
//            UserText(user = test.creator)
//            Text(text = stringResource(Res.string.start_at, test.start_at.toHumanReadable()))
//            Text(text = stringResource(Res.string.end_at, test.end_at.toHumanReadable()))
//            Text(text = stringResource(choosePlural(test.questions.size), test.questions.size))
//        }
            TextField(
                value = testViewModel.testName,
                onValueChange = { newTestName ->
                    testViewModel.testName = newTestName
                },
                modifier = Modifier.padding(horizontal = 10.dp),
                label = { Text(text = "Укажите тему теста (экзамен, ...)") }
            )

//            Row {
            // add it to foldable objects
//                DateAndTimePicker(
//                    title = { Text(text = "Дата и время начала теста") },
//                    modifier = Modifier.padding(10.dp)
//                )

            DateAndTimePicker(
                instant = Clock.System.now().plus(1.toHours()),
                title = { Text(text = "Дата и время завершения теста") },
                modifier = Modifier.padding(10.dp) // .weight(1f)
            )
//            }

            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
                items(testViewModel.test.questions) { question ->
                    Question(question = question)
                }
            }

            Button(
                onClick = {
                    // TODO: Allow to add question via dialog?/ navigate to other screen
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(text = stringResource(Res.string.add_question_button))
            }

            if (testViewModel.test.questions.isNotEmpty()) {
                Button(
                    onClick = { testViewModel.saveTest() },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Text(text = stringResource(Res.string.save_test_button))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateAndTimePicker(
    instant: Instant = Clock.System.now(),
    title: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val dateTime = instant.toLocalDateTime()

    val endDateState = rememberDatePickerState(
        initialSelectedDateMillis = instant.toEpochMilliseconds(),
        initialDisplayMode = DisplayMode.Input
    )
    val endTimeState = rememberTimePickerState(initialHour = dateTime.hour)

    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DatePicker(
                state = endDateState,
                title = title,
//                headline = { },
//                showModeToggle = false
            )
            TimeInput(state = endTimeState)
        }
    }
}

@Composable
fun Question(question: Question, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(bottom = 10.dp)) {
        Text(text = question.text, modifier = Modifier.padding(5.dp))
    }
}
