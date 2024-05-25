package screens.test

import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.test_creation
import dev.boringx.compose.generated.resources.test_editing
import dev.boringx.compose.generated.resources.test_passing
import dev.boringx.compose.generated.resources.test_result
import org.jetbrains.compose.resources.StringResource

enum class TestScreenStatus(val res: StringResource) {
    Creation(res = Res.string.test_creation),
    Editing(res = Res.string.test_editing),
    Passing(res = Res.string.test_passing),
    Result(res = Res.string.test_result),
}