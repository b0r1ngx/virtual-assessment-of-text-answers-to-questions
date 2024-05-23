package screens.test

import Question
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.Subtitle
import components.Title
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.add_question_button
import dev.boringx.compose.generated.resources.save_test_button
import dev.boringx.compose.generated.resources.test
import dev.boringx.compose.generated.resources.test_creation
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.stringResource
import screens.utils.toLocalDateTime
import styles.RoundedCornerBy16

@Composable
fun EditingTestScreen(testViewModel: EditingTestViewModel) {
    val isStartAtExpanded = remember { mutableStateOf(false) }
    val isEndAtExpanded = remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Column {
        Row {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = testViewModel.onFinished)
            )

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
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 1.dp),
            thickness = 1.dp,
            color = Color.Black,
        )

        TextField(
            value = testViewModel.name,
            onValueChange = { newTestName -> testViewModel.name = newTestName },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .focusRequester(focusRequester),
            label = { Text(text = "Укажите тему и/или тип теста") } // Type the topic and/or type of a test
        )

        // TODO: Allow to choose course from User.courses via Picker

        // TODO: Later, drop default time values and until teacher provide it, don't allow to save the test.
        DateAndTimePickerCard(
            title = "Дата и время начала теста", // Date and time test start
            isExpanded = isStartAtExpanded,
            modifier = Modifier.padding(10.dp)
        )

        DateAndTimePickerCard(
            title = "Дата и время завершения теста", // Date and time test finishing
            isExpanded = isEndAtExpanded,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
        )

        Subtitle(text = "Вопросы", modifier = Modifier.fillMaxWidth())
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 1.dp),
            thickness = 1.dp,
            color = Color.Black,
        )
        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 10.dp)) {
            items(testViewModel.questions.value ?: listOf()) { question ->
                Question(question = question)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Button(
                onClick = {
                    // TODO: Allow to add question via dialog?
                },
            ) {
                Text(text = stringResource(Res.string.add_question_button))
            }

            Button(
                onClick = { testViewModel.saveTest() },
                enabled = testViewModel.questions.value?.isNotEmpty() ?: false,
            ) {
                Text(text = stringResource(Res.string.save_test_button))
            }
        }
    }
}

@Composable
private fun DateAndTimePickerCard(
    title: String,
    isExpanded: MutableState<Boolean>,
    instant: Instant = Clock.System.now(),
    modifier: Modifier = Modifier
) {
    val onExpand = { isExpanded.value = !isExpanded.value }
    Card(modifier = modifier, shape = RoundedCornerBy16) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onExpand).padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = title)
            Icon(
                imageVector = if (isExpanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }

        if (isExpanded.value)
            DateAndTimePicker(instant = instant)
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
        initialDisplayMode = DisplayMode.Input,
    )
    val endTimeState = rememberTimePickerState(initialHour = dateTime.hour)

    Column(
        modifier = modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DatePicker(state = endDateState, title = title)
        TimeInput(state = endTimeState)
    }
}

@Composable
fun Question(question: Question, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(bottom = 10.dp)) {
        Text(text = question.text, modifier = Modifier.padding(5.dp))
    }
}
