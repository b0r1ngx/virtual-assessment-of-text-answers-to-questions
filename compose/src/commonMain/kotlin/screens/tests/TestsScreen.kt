package screens.tests

import TestModel
import UserViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.Title
import components.UserText
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.create_test
import dev.boringx.compose.generated.resources.end_at
import dev.boringx.compose.generated.resources.no_available_tests
import dev.boringx.compose.generated.resources.questions
import dev.boringx.compose.generated.resources.start_at
import dev.boringx.compose.generated.resources.tests
import kotlinx.datetime.Clock
import model.UserType
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import screens.tests.tabs.TestsTab
import screens.utils.toHumanReadable
import styles.RoundedCornerBy16

@Composable
fun TestsScreen(
    userViewModel: UserViewModel,
    testsViewModel: TestsViewModel
) {
    val selectedTab = remember { mutableStateOf(TestsTab.Available) }
    val tests by testsViewModel.tests.collectAsState()

    Column {
        Title(
            text = stringResource(Res.string.tests),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
        )

        // TODO: if user is teacher, tabbar must have other tabs
        // TeacherTabs:
        TabBar(
            selectedTab = selectedTab,
            modifier = Modifier.fillMaxWidth()
        )

        // TODO: if user is teacher, filter must work by other rules
        Tests(
            selectedTab = selectedTab.value,
            tests = tests,
            onTestClick = testsViewModel.onTestClick,
            modifier = Modifier
                .weight(1f)
                .background(color = MaterialTheme.colorScheme.background.copy(alpha = .5f))
        )

        if (userViewModel.user.typeId == UserType.Teacher.ordinal) {
            Button(
                onClick = testsViewModel.onCreateTestClick,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(text = stringResource(Res.string.create_test))
            }
        }
    }
}

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
        containerColor = Color.Transparent,
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

@Composable
private fun Tests(
    selectedTab: TestsTab,
    tests: List<TestModel>,
    onTestClick: (TestModel) -> Unit,
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

            LazyColumn(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // divide by test.course
                items(items = tests.filter {
                    when (selectedTab) {
                        TestsTab.Available -> it.end_at > Clock.System.now()
                        TestsTab.Passed -> {
                            it.end_at < Clock.System.now()
                            // todo: also with || add condition, that there must goes test,
                            //  that user already passed, to available, same but with &&
                        }
                    }
                }) {
                    TestCard(test = it, onTestClick = onTestClick)
                }
            }
        }
    }
}

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

@Composable
private fun TestCard(
    test: TestModel,
    onTestClick: (TestModel) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: if teacher clicks on test that is not
    Card(
        onClick = { onTestClick(test) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        // true, for test.start_at < Clock.System.now()
        // for assessed - show questions to answers, virtual assessment mark, teacher mark, opinion
        // for awaited - show questions to answers, where mark should be - say: await of teacher assessment
        // for missed - show questions to student, dont allow teacher to edit
        // false, if test.start_at > Clock.System.now()
        enabled = true,
        shape = RoundedCornerBy16,
//        backgroundColor = Color.Yellow // Завершенные, зеленые - оцененные, желтые - ожидают оценки, красные - пропущенные?
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = test.name)
            UserText(user = test.creator)
            Text(text = stringResource(Res.string.start_at, test.start_at.toHumanReadable()))
            Text(text = stringResource(Res.string.end_at, test.end_at.toHumanReadable()))
            Text(
                text = pluralStringResource(
                    Res.plurals.questions,
                    test.questions.size,
                    test.questions.size
                )
            )
        }
    }
}
