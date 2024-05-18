package components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import users.User

@Composable
fun UserText(
    user: User,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.clickable {
        // TODO: Navigate to UserScreen
    }) {
        Icon(imageVector = Icons.Default.Person, contentDescription = "User Photo")
        Text(
            text = user.name,
        )
    }
}