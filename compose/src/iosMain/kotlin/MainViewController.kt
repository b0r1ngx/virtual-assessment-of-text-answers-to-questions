import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import root.RootComponent
import root.RootContent

@Suppress("unused", "FunctionName")
fun MainViewController(root: RootComponent): UIViewController =
    ComposeUIViewController {
        RootContent(component = root)
    }
