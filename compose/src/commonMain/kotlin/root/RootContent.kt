package root

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun RootContent(component: RootComponent) {
    MaterialTheme {
        Text(text = "Hello, Decompose !")
    }
}