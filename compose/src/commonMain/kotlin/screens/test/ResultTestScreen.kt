package screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import components.QuestionToAnswerToAvgMarkAiCard
import components.TopBar
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.test
import org.jetbrains.compose.resources.stringResource
import screens.test.model.TestScreenStatus

@Composable
fun ResultTestScreen(
    testViewModel: ResultTestViewModel,
) {
    Column {
        TopBar(
            title = stringResource(Res.string.test),
            subtitle = stringResource(TestScreenStatus.Result.res),
            onBackButtonClick = testViewModel.onFinished,
        )

        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 10.dp)) {
            items(testViewModel.finalAssessments.second.questionsToAnswers) { (question, answer) ->
                QuestionToAnswerToAvgMarkAiCard(question, answer)
            }
        }

        BottomBar(
            text = testViewModel.finalAssessments.first.text,
            mark = testViewModel.finalAssessments.first.mark,
            avgMarkAi = testViewModel.finalAssessments.second.avgMarkAi,
        )
    }
}

@Composable
private fun BottomBar(
    text: String,
    mark: Double,
    avgMarkAi: Double,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = CardDefaults.cardColors().containerColor)
            .padding(5.dp),
    ) {
        Row {
            Text(text = "Предварительная отметка ассистента: ")
            Text(text = avgMarkAi.toString(), fontWeight = FontWeight.Bold)
        }
        Row {
            Text(text = "Итоговая отметка преподавателя: ")
            Text(text = mark.toString(), fontWeight = FontWeight.Bold)
        }
        Text(text = "Объяснение преподавателя: $text")
    }
}