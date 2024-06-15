package root

import UserViewModel
import androidx.compose.material3.SnackbarHostState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import screens.auth.AuthViewModel
import screens.test.AnswersTestViewModel
import screens.test.AssessTestViewModel
import screens.test.EditingTestViewModel
import screens.test.PassingTestViewModel
import screens.test.ResultTestViewModel
import screens.tests.TestsViewModel

interface RootComponent {
    val navigationStack: Value<ChildStack<*, Child>>
    val userViewModel: UserViewModel
    val snackbarHostState: SnackbarHostState

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class Auth(val component: AuthViewModel) : Child()
        class Tests(val component: TestsViewModel) : Child()
        class EditingTest(val component: EditingTestViewModel) : Child()
        class PassingTest(val component: PassingTestViewModel) : Child()
        class AnswersTest(val component: AnswersTestViewModel) : Child()
        class AssessTest(val component: AssessTestViewModel) : Child()
        class ResultTest(val component: ResultTestViewModel) : Child()
    }
}