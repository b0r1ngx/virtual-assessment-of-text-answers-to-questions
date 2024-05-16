package screens.tests

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TestsScreen(testsViewModel: TestsViewModel) {
    Text(text = "TestsScreen") // title, style

    // Tabs - Available, Passed, Missed

    if (testsViewModel.uiState.value.isLoading) {

        return
    }

    if (testsViewModel.tests.isEmpty()) {
        // show empty container
    } else {
        // show tests, divided by course
    }
}

@Composable
private fun Test() {

}
