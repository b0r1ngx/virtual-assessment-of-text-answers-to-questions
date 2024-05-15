package screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.name
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AuthScreen(authViewModel: AuthViewModel) {
    Column {
        // pick-up student / teacher

        TextField(
            value = authViewModel.name,
            onValueChange = { input ->
                authViewModel.name = input
            },
            label = { Text(text = stringResource(Res.string.name)) },
        )

        // choose courses
    }
}