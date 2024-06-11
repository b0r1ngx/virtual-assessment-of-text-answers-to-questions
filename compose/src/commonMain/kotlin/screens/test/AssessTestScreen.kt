package screens.test

import Answer
import Question
import UserModel
import UserViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import components.TopBar
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.test
import org.jetbrains.compose.resources.stringResource
import screens.test.model.TestScreenStatus

@Composable
fun AssessTestScreen(
    userViewModel: UserViewModel,
    testViewModel: AssessTestViewModel,
) {
    Column {
        TopBar(
            title = stringResource(Res.string.test),
            subtitle = stringResource(TestScreenStatus.Assess.res),
            onBackButtonClick = testViewModel.onFinished,
        )

        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 10.dp)) {
            items(testViewModel.testAnswers.questionsToAnswers) { (question, answer) ->
                QuestionToAnswerCard(question, answer)
            }
        }

        BottomBar(
            user = userViewModel.user,
            testViewModel = testViewModel
        )
    }
}

@Composable
private fun QuestionToAnswerCard(question: Question, answer: Answer) {
    Card {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Вопрос:", fontWeight = FontWeight.Bold)
            Text(text = question.text)
            Text(text = "Ответ:", fontWeight = FontWeight.Bold)
            Text(text = answer.text)
            Text(
                text = "Предварительная оценка: ${answer.avgMarkAi}",
                modifier = Modifier.align(Alignment.End),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun BottomBar(
    user: UserModel,
    testViewModel: AssessTestViewModel
) {
    Column(modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)) {
        TextField(
            value = testViewModel.assessment,
            onValueChange = { inputAssess ->
                testViewModel.assessment = inputAssess
                testViewModel.validateAssessment()
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Опишите свою оценку") },
        )
        TextField(
            value = testViewModel.mark.toString(),
            onValueChange = { inputMark ->
                testViewModel.mark = inputMark.toDouble()
                testViewModel.validateAssessment()
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Отметка") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(
            onClick = { testViewModel.saveAssess(user) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = testViewModel.isAllowedToEndAssess
        ) {
            Text("Завершить оценивание")
        }
    }
}
