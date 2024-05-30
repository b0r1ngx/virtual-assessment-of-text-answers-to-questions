package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import UserModel

@Composable
fun UserScreen(user: UserModel) {
    Column {
        Row {
            Icon(imageVector = Icons.Default.Person, contentDescription = "UserModel Photo")
            Column {
                Text(text = user.name)
                Text(text = user.email)
            }
        }
    }
}