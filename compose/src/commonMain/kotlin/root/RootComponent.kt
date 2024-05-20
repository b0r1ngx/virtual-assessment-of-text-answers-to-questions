package root

import UserViewModel
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import screens.auth.AuthViewModel
import screens.test.TestViewModel
import screens.tests.TestsViewModel

interface RootComponent {
    val userViewModel: UserViewModel
    val navigationStack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class Auth(val component: AuthViewModel) : Child()
        class Test(val component: TestViewModel) : Child()
        class Tests(val component: TestsViewModel) : Child()
    }
}