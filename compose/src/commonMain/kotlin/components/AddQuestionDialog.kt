package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.cancel_button
import dev.boringx.compose.generated.resources.save_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddQuestionDialog(
    onConfirmRequest: (text: String) -> Unit,
    onDismissRequest: () -> Unit,
    text: String = "",
    modifier: Modifier = Modifier,
) {
    var input by mutableStateOf(
        TextFieldValue(text = text, selection = TextRange(text.length))
    )

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Dialog(onDismissRequest = onDismissRequest) {
        Column(modifier = modifier) {
            Text(text = "Введите текст вопроса")

            TextField(
                value = input,
                onValueChange = { newTestName -> input = newTestName },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .focusRequester(focusRequester),
                label = { Text(text = "Укажите тему и/или тип теста") } // Type the topic and/or type of a test
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)) {
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(Res.string.cancel_button))
                }

                TextButton(onClick = { onConfirmRequest(text) }) {
                    Text(text = stringResource(Res.string.save_button))
                }
            }
        }
    }
}