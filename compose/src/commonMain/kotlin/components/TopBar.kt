package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String,
    subtitle: String,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Box(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(horizontal = 10.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(onClick = onBackButtonClick)
                    .padding(10.dp)
            )

            Column(modifier = Modifier.align(Alignment.Center)) {
                Title(text = title, modifier = Modifier.fillMaxWidth())
                Subtitle(text = subtitle, modifier = Modifier.fillMaxWidth())
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 1.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}