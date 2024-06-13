package screens.test

import Assessment
import ClientRepository
import TestAnswers
import kotlin.coroutines.CoroutineContext

class ResultTestViewModel(
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    val finalAssessments: Pair<Assessment, TestAnswers>,
    val onFinished: () -> Unit,
) {


}