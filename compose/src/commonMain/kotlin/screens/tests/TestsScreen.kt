package screens.tests

import Test
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.Title
import components.UserText
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.end_at
import dev.boringx.compose.generated.resources.no_available_tests
import dev.boringx.compose.generated.resources.start_at
import dev.boringx.compose.generated.resources.tests
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import screens.tests.tabs.TestsTab
import screens.utils.choosePlural
import screens.utils.toHumanReadable

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TestsScreen(testsViewModel: TestsViewModel) {
    val selectedTab = remember { mutableStateOf(TestsTab.Available) }

    Column {
        Title(
            text = stringResource(Res.string.tests),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.surface)
        )

        // if user is teacher, tabbar must have other tabs
        // TeacherTabs:
        TabBar(
            selectedTab = selectedTab,
            modifier = Modifier.fillMaxWidth()
        )

        // if user is teacher, filter must work by other rules
        Tests(
            selectedTab = selectedTab.value,
            tests = testsViewModel.tests,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background.copy(alpha = .5f))
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TabBar(
    selectedTab: MutableState<TestsTab>,
    modifier: Modifier = Modifier
) {
    val tabs = remember { TestsTab.entries.toTypedArray() }
    var selectedIndex by remember(selectedTab) {
        mutableIntStateOf(tabs.indexOf(selectedTab.value))
    }
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        backgroundColor = Color.Transparent,
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    selectedTab.value = tab
                },
                text = { Text(text = stringResource(tab.res)) },
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
        modifier = modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (tests.isEmpty()) {
            Text(text = stringResource(Res.string.no_available_tests))
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TestStatus.entries.forEach {
                    TestStatus(status = it)
                }
            }

            LazyColumn(verticalArrangement = Arrangement.SpaceBetween) {
                // divide by test.course
                items(items = tests.filter {
                    when (selectedTab) {
                        TestsTab.Available -> it.end_at > Clock.System.now()
                        TestsTab.Passed -> it.end_at < Clock.System.now()
                    }
                }) {
                    TestCard(test = it, onTestClick = {
                        // TODO: navigate to TestScreen,
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TestStatus(
    status: TestStatus,
    modifier: Modifier = Modifier
) { // TODO: box and text not centered, textAlign not helping, fix it
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(15.dp)
                .clip(RectangleShape)
                .background(color = status.color)
        )
        Text(
            text = stringResource(status.res),
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
private fun TestCard(
    test: Test,
    onTestClick: (Test) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onTestClick(test) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        enabled = false, // true only for greens, greens - оцененные, user.id == test.student_id -- think about how to make different, instead of check declared things
        shape = RoundedCornerShape(16.dp),
//        backgroundColor = Color.Yellow // Завершенные, зеленые - оцененные, желтые - ожидают оценки, красные - пропущенные?
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(5.dp)
        ) {
            Text(text = test.name)
            UserText(user = test.creator)
            // TODO: Bug appear, can't provide arguments for strings, check why
            Text(text = stringResource(Res.string.start_at, test.start_at.toHumanReadable()))
            Text(text = stringResource(Res.string.end_at, test.end_at.toHumanReadable()))
            Text(text = stringResource(choosePlural(test.questions.size), test.questions.size))
        }
    }
}
