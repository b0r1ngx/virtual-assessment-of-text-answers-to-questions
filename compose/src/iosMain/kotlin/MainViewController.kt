import androidx.compose.ui.window.ComposeUIViewController

@Suppress("unused", "FunctionName")
fun MainViewController() =
    ComposeUIViewController {
        App(sqlDriverFactory = SqlDriverFactory())
    }
