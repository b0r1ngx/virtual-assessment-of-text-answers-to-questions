package screens.test

import ClientRepository
import TestModel
import com.arkivanov.decompose.ComponentContext
import kotlin.coroutines.CoroutineContext

class AssessTestViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    val test: TestModel,
    val onFinished: () -> Unit,
) {

}