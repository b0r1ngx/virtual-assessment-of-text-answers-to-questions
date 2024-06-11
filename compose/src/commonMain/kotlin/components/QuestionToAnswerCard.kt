package components

import Answer
import Question
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun QuestionToAnswerCard(question: Question, answer: Answer) {
    Card {
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