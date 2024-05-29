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

        setContent {
            RootContent(component = provideRootComponent())
        }
    }

    private fun provideRootComponent(): DefaultRootComponent {
        return DefaultRootComponent(
            sqlDriverFactory = SqlDriverFactory(context = this),
            componentContext = defaultComponentContext()
        )
    }

}
