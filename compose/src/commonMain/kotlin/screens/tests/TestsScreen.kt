package screens.tests

import Test
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import components.Title
import components.UserText
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.end_at
import dev.boringx.compose.generated.resources.no_available_tests
import dev.boringx.compose.generated.resources.questions
import dev.boringx.compose.generated.resources.start_at
import dev.boringx.compose.generated.resources.tests
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.tests.tabs.TestsTab

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TestsScreen(testsViewModel: TestsViewModel) {
    val selectedTab by remember { mutableStateOf(TestsTab.Available) }

    Column {
        Title(
            text = stringResource(Res.string.tests),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.surface)
        )

        TabBar(
            selectedTab = selectedTab,
            modifier = Modifier.fillMaxWidth()
        )

        Tests(
            selectedTab = selectedTab,
            tests = testsViewModel.tests,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background.copy(alpha = .5f))
        )
    }
}

@Composable
private fun TabBar(
    selectedTab: TestsTab,
    modifier: Modifier = Modifier
) {
    val tabs = remember { TestsTab.entries.toTypedArray() }
    var selectedIndex by remember(selectedTab) {
        mutableIntStateOf(tabs.indexOf(selectedTab))
    }
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        backgroundColor = Color.Transparent,
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = selectedIndex == index,
                onClick = { selectedIndex = index },
                text = { Text(text = tab.name) },
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun Tests(
    selectedTab: TestsTab,
    tests: List<Test>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (tests.isEmpty()) {
            Text(text = stringResource(Res.string.no_available_tests))
        } else {
            LazyColumn(verticalArrangement = Arrangement.SpaceBetween) {
                // divide by test.course
                items(items = tests.filter {
                    when (selectedTab) {
                        TestsTab.Available -> it.end_at > Clock.System.now()
                        TestsTab.Passed -> {
                            // TODO: search in studentTests
                            it.end_at < Clock.System.now()
                        }

                        TestsTab.Missed -> {
                            // gone
                            it.end_at < Clock.System.now()
                            // and not passed
                        }
                    }

                }) {
                    TestCard(test = it)
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TestCard(
    test: Test,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(text = test.name)
            UserText(user = test.creator)
            // TimeText: use .format to provide DateTimeFormat
            Text(text = stringResource(Res.string.start_at) + "${test.start_at}")
            Text(text = stringResource(Res.string.end_at) + "${test.end_at}")
            Text(text = "${test.questions.size}" + stringResource(Res.string.questions))
        }
    }
}

//@Preview
//@Composable
//fun TestsScreenPreview() = TestsScreen()