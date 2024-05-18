package screens.tests

import Test
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import components.UserText
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.end_at
import dev.boringx.compose.generated.resources.questions
import dev.boringx.compose.generated.resources.start_at
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.tests.tabs.TestsTab

@Composable
fun TestsScreen(testsViewModel: TestsViewModel) {
    val selectedTab = remember { mutableStateOf(TestsTab.Available) }

    Column {
        Text(
            text = "TestsScreen",
            style = MaterialTheme.typography.h2,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background),
        ) // title, style

        CustomTabs(selectedTab = selectedTab)

//        if (testsViewModel.uiState.value.isLoading) {
//            return
//        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (testsViewModel.tests.isEmpty()) {
                // show empty container
                Text(text = "No available tests") // show it in center
            } else {
                // show tests, divided by course
                LazyColumn {
                    // divide by test.course
                    items(items = testsViewModel.tests) {
                        TestCard(test = it)
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomTabs(
    selectedTab: MutableState<TestsTab>,
    modifier: Modifier = Modifier
) {
    Row {
        TestsTab.entries.forEach {
            Tab(
                selected = it == selectedTab.value,
                onClick = { selectedTab.value = it }
            )
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TestCard(
    test: Test,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column {
            Text(text = test.name)
            UserText(user = test.creator)
            Row {
                Row(modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(Res.string.start_at))
                    Text(text = test.start_at.toString())
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(Res.string.end_at))
                    Text(text = test.end_at.toString()) // use .format to provide DateTimeFormat
                }
            }
            Row {
                Text(text = test.questions.size.toString())
                Text(text = stringResource(Res.string.questions))
            }
        }
    }
}

//@Preview
//@Composable
//fun TestsScreenPreview() = TestsScreen()