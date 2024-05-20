import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import client.Repository
import client.network.ClientApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.auth.AuthScreen
import screens.auth.AuthViewModel
import screens.tests.TestsScreen
import screens.tests.TestsViewModel

@Composable
@Preview
fun App(sqlDriverFactory: SqlDriverFactory) {
    val repository = Repository(
        database = createDatabase(sqlDriverFactory),
        api = ClientApi()
    )
    val userViewModel = UserViewModel(repository)
    val authViewModel = AuthViewModel(repository) // provide it with userViewModel::setUser
    val testsViewModel = TestsViewModel(repository)

    MaterialTheme {
//        AuthScreen(authViewModel)
        TestsScreen(userViewModel, testsViewModel)
    }
}
