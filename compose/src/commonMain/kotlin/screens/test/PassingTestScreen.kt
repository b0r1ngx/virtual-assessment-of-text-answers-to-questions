package screens.test

import Answer
import Question
import UserModel
import UserViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import components.TopBar
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.complete_test
import dev.boringx.compose.generated.resources.save_button
import dev.boringx.compose.generated.resources.test
import org.jetbrains.compose.resources.stringResource
import screens.test.model.TestScreenStatus
import styles.GreenPastelColor
import styles.YellowPastelColor

@Composable
fun PassingTestScreen(
    userViewModel: UserViewModel,
    testViewModel: PassingTestViewModel
) {
    Column {
        TopBar(
            title = stringResource(Res.string.test),
            subtitle = stringResource(TestScreenStatus.Passing.res),
            onBackButtonClick = testViewModel.onFinished,
        )

        // info about test with questions

        // if testViewModel.testInProgress draw one things, else draw info about test
        // EditingTest.info, like at card
        Column {
            // progressBar?
            LazyColumn {
                items(testViewModel.questionsToAnswers.value) {
                    QuestionWithAnswerField(
                        user = userViewModel.user,
                        questionToAnswer = it,
                        saveAnswers = testViewModel::saveAnswers
                    )
                }
            }
        }

        Button(onClick = {
            testViewModel.saveAnswers(
                user = userViewModel.user,
                answers = testViewModel.questionsToAnswers.value,
                isTestCompleted = true
            )
        }) {
            Text(stringResource(Res.string.complete_test))
        }
    }
}

@Composable
private fun QuestionWithAnswerField(
    user: UserModel,
    questionToAnswer: Pair<Question, Answer>,
    saveAnswers: (user: UserModel, answer: List<Pair<Question, Answer>>, isTestCompleted: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (question, answer) = questionToAnswer
    var answerFieldValue by mutableStateOf(
        TextFieldValue(text = answer.text, selection = TextRange(answer.text.length))
    )
    var isExpanded by remember { mutableStateOf(false) }
    var questionColorState by remember { mutableStateOf(YellowPastelColor) }

    Card(
        modifier = modifier.padding(bottom = 10.dp),
        colors = CardDefaults.cardColors().copy(containerColor = questionColorState)
    ) {
        Text(
            text = question.text,
            modifier = Modifier.padding(5.dp).clickable { isExpanded = !isExpanded },
        )

        if (isExpanded) {
            TextField(
                value = answerFieldValue,
                onValueChange = { newAnswer -> answerFieldValue = newAnswer },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp),
                label = { Text(text = "Поле для ответа") },
            )

            Button(
                onClick = {
                    saveAnswers(
                        user,
                        listOf(question to Answer(text = answerFieldValue.text)),
                        false
                    )
                    questionColorState = GreenPastelColor
                    isExpanded = false
                },
            ) {
                Text(text = stringResource(Res.string.save_button))
            }
        }
    }
}