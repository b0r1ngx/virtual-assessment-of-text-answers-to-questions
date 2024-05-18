package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import users.User

@Composable
fun UserScreen(user: User) {
    Column {
        Row {
            Icon(imageVector = Icons.Default.Person, contentDescription = "User Photo")
            Column {
                Text(text = user.name)
                Text(text = user.email)
            }
        }
    }
}