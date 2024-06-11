package screens.test

import Course
import Question
import UserViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.AddQuestionDialog
import components.Subtitle
import components.TopBar
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.add_question_button
import dev.boringx.compose.generated.resources.save_test_button
import dev.boringx.compose.generated.resources.test
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.stringResource
import screens.test.model.TestScreenStatus
import screens.utils.toLocalDateTime
import styles.RoundedCornerBy16

// TODO: fix bug, when we create test and navigated to tests screens, its not appeared in tests
//  i think it also not update values of edited test, after we edit test
@Composable
fun EditingTestScreen(
    userViewModel: UserViewModel,
    testViewModel: EditingTestViewModel,
) {
    val isStartAtExpanded = remember { mutableStateOf(false) }
    val isEndAtExpanded = remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        if (testViewModel.test == null) {
            focusRequester.requestFocus()
        }
    }

    var pickedQuestion by remember { mutableStateOf<Pair<Int?, Question?>>(null to null) }
    var isDialogOpened by remember { mutableStateOf(false) }

    Column {
        TopBar(
            title = stringResource(Res.string.test),
            subtitle = stringResource(TestScreenStatus.Editing.res),
            onBackButtonClick = testViewModel.onFinished,
        )

        // TODO: create strings: Type the topic and/or type of a test
        TextField(
            value = testViewModel.name,
            onValueChange = { newTestName -> testViewModel.name = newTestName },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .focusRequester(focusRequester),
            label = { Text(text = "Укажите тему и/или тип теста") }
        )

        ChooseCourse(
            initialCourseText = testViewModel.course?.name ?: "",
            courses = userViewModel.user.courses,
            onCourseClick = { pickedCourse ->
                testViewModel.course = pickedCourse
            },
            modifier = Modifier.padding(5.dp)
        )

        // TODO: Later, drop default time values and until teacher provide it, don't allow to save the test.
        // TODO: create strings: Date and time test start
        DateAndTimePickerCard(
            title = "Дата и время начала теста",
            isExpanded = isStartAtExpanded,
            at = testViewModel.startAt,
            onExpandClosed = testViewModel::saveDateAndTime,
            modifier = Modifier.padding(10.dp)
        )

        // TODO: create strings: Date and time test finishing
        DateAndTimePickerCard(
            title = "Дата и время завершения теста",
            isExpanded = isEndAtExpanded,
            at = testViewModel.endAt,
            onExpandClosed = testViewModel::saveDateAndTime,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
        )

        Subtitle(text = "Вопросы", modifier = Modifier.fillMaxWidth())
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 5.dp),
            thickness = 1.dp,
            color = Color.Black,
        )
        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 10.dp)) {
            println("questions: ${testViewModel.questions.value}")
            itemsIndexed(testViewModel.questions.value) { index, question ->
                Question(
                    question = question,
                    modifier = Modifier.clickable {
                        pickedQuestion = index to question
                        isDialogOpened = true
                    }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Button(
                onClick = {
                    pickedQuestion = null to null
                    isDialogOpened = true
                },
            ) {
                Text(text = stringResource(Res.string.add_question_button))
            }

            Button(
                onClick = { testViewModel.saveTest(userViewModel.user) },
                enabled = testViewModel.questions.value.isNotEmpty(),
            ) {
                Text(text = stringResource(Res.string.save_test_button))
            }
        }

        if (isDialogOpened) {
            AddQuestionDialog(
                onConfirmRequest = { questionText ->
                    testViewModel.upsertQuestion(
                        text = questionText,
                        index = pickedQuestion.first
                    )
                },
                onDismissRequest = { isDialogOpened = false },
                text = pickedQuestion.second?.text ?: ""
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChooseCourse(
    initialCourseText: String,
    courses: List<Course>,
    onCourseClick: (course: Course) -> Unit,
    modifier: Modifier = Modifier,
) {
    var pickedCourseText by remember { mutableStateOf(initialCourseText) }
    var isExpanded by remember { mutableStateOf(false) }
    Box {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
            modifier = modifier,
        ) {
            TextField(
                value = pickedCourseText,
                onValueChange = { inputPickedCourseText ->
                    pickedCourseText = inputPickedCourseText
                },
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                singleLine = true,
                label = { Text("Назначить курс") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
            ) {
                courses.forEach { course ->
                    DropdownMenuItem(
                        text = { Text(text = course.name) },
                        onClick = {
                            pickedCourseText = course.name
                            onCourseClick(course)
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateAndTimePickerCard(
    title: String,
    isExpanded: MutableState<Boolean>,
    at: MutableState<Instant>,
    onExpandClosed: (at: MutableState<Instant>, dateMillis: Long, hour: Int, minute: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val initialDateTime = at.value.toLocalDateTime()
    val initialMillis = at.value.toEpochMilliseconds()
    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis, initialDisplayMode = DisplayMode.Input,
    )
    val timeState = rememberTimePickerState(initialHour = initialDateTime.hour)

    val onExpand = {
        isExpanded.value = !isExpanded.value

        // selectedDateMillis can't be null, because we set initials for dateState
        //  (maybe can, not check the source implementation)
        if (!isExpanded.value)
            onExpandClosed(
                at,
                dateState.selectedDateMillis ?: initialMillis,
                timeState.hour,
                timeState.hour
            )
    }

    Card(modifier = modifier, shape = RoundedCornerBy16) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onExpand).padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = title)
            ExposedDropdownMenuDefaults.TrailingIcon(isExpanded.value)
        }

        if (isExpanded.value)
            DateAndTimePicker(dateState = dateState, timeState = timeState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateAndTimePicker(
    dateState: DatePickerState,
    timeState: TimePickerState,
    title: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DatePicker(state = dateState, title = title)
        TimeInput(state = timeState)
    }
}

@Composable
fun Question(question: Question, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(bottom = 10.dp)) {
        Text(text = question.text, modifier = Modifier.padding(5.dp))
    }
}
