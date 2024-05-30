package screens.test

import Answer
import ClientRepository
import Question
import TestAnswers
import TestModel
import UserModel
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PassingTestViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    val test: TestModel,
    val onFinished: () -> Unit,
) : ComponentContext by componentContext {

    private val scope = coroutineScope(mainCoroutineContext + SupervisorJob())

    val questionsToAnswers =
        mutableStateOf(mutableListOf<Pair<Question, Answer>>())

    init {
        test.questions.forEach { question ->
            questionsToAnswers.value.add(question to Answer(text = ""))
        }
    }

    fun saveAnswers(
        user: UserModel,
        answers: List<Pair<Question, Answer>>,
        isTestCompleted: Boolean,
    ) {
        // TODO: later save each answered question
        if (isTestCompleted) {
            val testAnswers = TestAnswers(test.id, user, answers)
            scope.launch {
                repository.saveAnswers(
                    testAnswers = testAnswers,
                    isTestCompleted = isTestCompleted
                )
            }
            return
        }
        answers.forEach { questionToAnswer ->
            val questionToAnswerIndex = questionsToAnswers.value
                .indexOfFirst { it.first.id == questionToAnswer.first.id }
            questionsToAnswers.value.set(
                index = questionToAnswerIndex,
                element = questionToAnswer
            )
        }
    }

}
