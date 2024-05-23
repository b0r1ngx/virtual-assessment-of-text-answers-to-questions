package screens.test

import Test
import client.ClientRepository
import kotlin.coroutines.CoroutineContext

class ResultTestViewModel(
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    test: Test,
    val onFinished: () -> Unit,
) {
}