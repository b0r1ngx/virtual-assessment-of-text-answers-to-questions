package screens.auth

import Course
import UserViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.choose_courses
import dev.boringx.compose.generated.resources.choose_role
import dev.boringx.compose.generated.resources.email
import dev.boringx.compose.generated.resources.name
import dev.boringx.compose.generated.resources.register
import model.UserType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AuthScreen(
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel
) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = stringResource(Res.string.choose_role),
            style = MaterialTheme.typography.h6
        )
        TabBar(
            selectedTab = authViewModel.userType,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Укажите свои данные",
            modifier = Modifier.padding(top = 20.dp),
            style = MaterialTheme.typography.h6
        )
        TextField(
            value = authViewModel.name,
            onValueChange = { inputName -> authViewModel.name = inputName },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(Res.string.name)) },
            singleLine = true,
        )
        TextField(
            value = authViewModel.email,
            onValueChange = { inputEmail -> authViewModel.email = inputEmail },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(Res.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
        )

        Text(
            text = stringResource(Res.string.choose_courses),
            modifier = Modifier.padding(top = 20.dp),
            style = MaterialTheme.typography.h6
        )
        LazyColumn {
            items(authViewModel.courses) { course ->
                CourseCheckbox(
                    course = course,
                    onCourseClick = { checked ->
                        if (checked) authViewModel.pickedCourses.add(course)
                        else authViewModel.pickedCourses.remove(course)
                    }
                )
            }
        }

        Button(
            onClick = { authViewModel.registerUser() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(Res.string.register))
        }
    }
}

// TODO: Same function like at TestsScreen, change func signature and place to components/
@OptIn(ExperimentalResourceApi::class)
@Composable
private fun TabBar(
    selectedTab: MutableState<UserType>,
    modifier: Modifier = Modifier
) {
    val tabs = remember { UserType.entries.toTypedArray() }
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

@Composable
private fun CourseCheckbox(
    course: Course,
    onCourseClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var checked by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = {
            onCourseClick(it)
            checked = !checked
        })
        Text(
            text = course.name,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}