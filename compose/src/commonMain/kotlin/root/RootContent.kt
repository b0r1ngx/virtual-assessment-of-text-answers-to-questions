package root

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import screens.auth.AuthScreen
import screens.test.EditingTestScreen
import screens.test.PassingTestScreen
import screens.tests.TestsScreen
import theme.AppTheme

// TODO: Use Scaffold
@Composable
internal fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    AppTheme {
        Surface(modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
            Children(
                stack = component.navigationStack,
                modifier = Modifier.fillMaxSize(),
                animation = stackAnimation(fade() + scale())
            ) {
                when (val instance = it.instance) {
                    is RootComponent.Child.Auth -> AuthScreen(authViewModel = instance.component)

                    is RootComponent.Child.Tests -> TestsScreen(
                        userViewModel = component.userViewModel,
                        testsViewModel = instance.component,
                    )

                    is RootComponent.Child.EditingTest -> EditingTestScreen(
                        userViewModel = component.userViewModel,
                        testViewModel = instance.component,
                    )

                    is RootComponent.Child.PassingTest -> PassingTestScreen(
                        userViewModel = component.userViewModel,
                        testViewModel = instance.component,
                    )

                    is RootComponent.Child.AssessTest -> TODO()
                    is RootComponent.Child.ResultTest -> TODO()
                }
            }
        }
    }
}