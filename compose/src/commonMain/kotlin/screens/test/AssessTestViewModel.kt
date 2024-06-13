package screens.test

import Assessment
import TestAnswers
import UserModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext

class AssessTestViewModel(
    componentContext: ComponentContext,
    val testAnswers: TestAnswers,
    val onAssess: (assessment: Assessment) -> Unit,
    val onFinished: () -> Unit,
) : ComponentContext by componentContext {

    var assessment by mutableStateOf("")
    var mark: Double by mutableStateOf(-1.0)

    var isAllowedToEndAssess by mutableStateOf(false)

    fun validateAssessment() {
        isAllowedToEndAssess = assessment.isNotBlank() && mark >= 0
    }

    fun saveAssess(user: UserModel) {
        val assessment = Assessment(
            testId = testAnswers.testId,
            teacherEmail = user.email,
            studentEmail = testAnswers.user.email,
            text = assessment,
            mark = mark
        )
        onAssess(assessment)
    }
}
