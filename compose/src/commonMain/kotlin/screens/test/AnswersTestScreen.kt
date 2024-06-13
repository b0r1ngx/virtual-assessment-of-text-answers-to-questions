package screens.test

import TestAnswers
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import components.TopBar
import components.UserText
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.test
import org.jetbrains.compose.resources.stringResource
import styles.RoundedCornerBy16
import styles.YellowPastelColor
import utils.round

@Composable
fun AnswersTestScreen(
    testViewModel: AnswersTestViewModel,
) {
    Column {
        TopBar(
            title = stringResource(Res.string.test),
            subtitle = "Ответы студентов",
            onBackButtonClick = testViewModel.onFinished,
        )

        // allow to filter by surname, avgMarkAi, already assessed
        // list of students
        LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
            items(testViewModel.studentsPassedTest.value) { testAnswers ->
                StudentCard(
                    testAnswers = testAnswers,
                    onStudentClick = testViewModel.onStudentClick,
                )
            }
        }
    }
}

@Composable
private fun StudentCard(
    testAnswers: TestAnswers,
    onStudentClick: (testAnswer: TestAnswers) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { onStudentClick(testAnswers) },
        modifier = modifier.fillMaxWidth().padding(bottom = 10.dp),
        shape = RoundedCornerBy16,
        // TODO: зеленые - оцененные, желтые - ожидают оценки
        colors = CardDefaults.cardColors().copy(containerColor = YellowPastelColor),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
        ) {
            UserText(user = testAnswers.user)
            Text(
                text = "Предварительная оценка: ${testAnswers.avgMarkAi.round(1)}",
                modifier = Modifier.align(Alignment.End)
            )
            // TODO: if possible (test is assessed), show finally user (teacher) mark
        }
    }
}
