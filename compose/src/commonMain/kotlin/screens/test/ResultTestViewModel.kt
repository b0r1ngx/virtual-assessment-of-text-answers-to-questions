package screens.test

import TestModel
import client.ClientRepository
import kotlin.coroutines.CoroutineContext

class ResultTestViewModel(
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    test: TestModel,
    val onFinished: () -> Unit,
) {
}