package screens.test

import ClientRepository
import TestModel
import kotlin.coroutines.CoroutineContext

class ResultTestViewModel(
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    test: TestModel,
    val onFinished: () -> Unit,
) {
}