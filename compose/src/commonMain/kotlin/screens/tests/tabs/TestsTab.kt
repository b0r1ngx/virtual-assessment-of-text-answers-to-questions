package screens.tests.tabs

import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.tests_tab_available
import dev.boringx.compose.generated.resources.tests_tab_passed
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalResourceApi::class)
enum class TestsTab(val res: StringResource) {
    Available(res = Res.string.tests_tab_available), // Доступные
    Passed(res = Res.string.tests_tab_passed), // Завершенные, зеленые - оцененные, желтые - ожидают оценки, красные - пропущенные?
}
