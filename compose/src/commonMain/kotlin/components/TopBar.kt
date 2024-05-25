package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TopBar(
    title: String,
    subtitle: String,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.background(color = MaterialTheme.colorScheme.surface)) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = onBackButtonClick)
        )

        Column {
            Title(text = title, modifier = Modifier.fillMaxWidth())
            Subtitle(text = subtitle, modifier = Modifier.fillMaxWidth())
        }
    }
}