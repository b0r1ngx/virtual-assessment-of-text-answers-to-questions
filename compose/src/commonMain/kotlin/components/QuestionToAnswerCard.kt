package components

import Answer
import Question
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import styles.RoundedCornerBy16

@Composable
fun QuestionToAnswerCard(
    question: Question,
    answer: Answer,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(5.dp),
        shape = RoundedCornerBy16,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Вопрос:", fontWeight = FontWeight.Bold)
            Text(text = question.text)
            Text(text = "Ответ:", fontWeight = FontWeight.Bold)
            Text(text = answer.text)
            Text(
                text = "Предварительная оценка: ${answer.avgMarkAi}",
                modifier = Modifier.align(Alignment.End),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}