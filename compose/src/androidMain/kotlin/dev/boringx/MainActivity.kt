package dev.boringx

import SqlDriverFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import root.DefaultRootComponent
import root.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = DefaultRootComponent(
            sqlDriverFactory = SqlDriverFactory(context = this),
            componentContext = defaultComponentContext()
        )

        setContent {
            RootContent(component = root)
        }
    }
}
