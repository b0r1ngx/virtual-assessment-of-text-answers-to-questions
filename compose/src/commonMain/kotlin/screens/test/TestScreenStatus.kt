package screens.test

import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.creation
import dev.boringx.compose.generated.resources.editing
import dev.boringx.compose.generated.resources.passing
import dev.boringx.compose.generated.resources.assessment
import dev.boringx.compose.generated.resources.result
import org.jetbrains.compose.resources.StringResource

enum class TestScreenStatus(val res: StringResource) {
    Creation(res = Res.string.creation),
    Editing(res = Res.string.editing),
    Passing(res = Res.string.passing),
    Assess(res = Res.string.assessment),
    Result(res = Res.string.result),
}