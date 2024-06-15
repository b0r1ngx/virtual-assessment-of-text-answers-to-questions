package root

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import screens.auth.AuthScreen
import screens.test.AnswersTestScreen
import screens.test.AssessTestScreen
import screens.test.EditingTestScreen
import screens.test.PassingTestScreen
import screens.test.ResultTestScreen
import screens.tests.TestsScreen
import theme.AppTheme

private val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("No Snackbar Host State provided")
}

@Composable
internal fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { component.snackbarHostState }

    CompositionLocalProvider(
        LocalSnackbarHostState provides snackbarHostState,
    ) {
        AppTheme {
            Scaffold(
                modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars),
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            ) { paddingValues: PaddingValues ->
                Children(
                    stack = component.navigationStack,
                    modifier = Modifier.consumeWindowInsets(paddingValues).fillMaxSize(),
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

                        is RootComponent.Child.AnswersTest -> AnswersTestScreen(testViewModel = instance.component)

                        is RootComponent.Child.AssessTest -> AssessTestScreen(
                            userViewModel = component.userViewModel,
                            testViewModel = instance.component
                        )

                        is RootComponent.Child.ResultTest -> ResultTestScreen(testViewModel = instance.component)
                    }
                }
            }
        }
    }
}