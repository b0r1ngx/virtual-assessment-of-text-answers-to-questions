package screens.tests

import androidx.compose.ui.graphics.Color
import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.test_status_assessed
import dev.boringx.compose.generated.resources.test_status_await
import dev.boringx.compose.generated.resources.test_status_missed
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import styles.GreenPastelColor
import styles.RedPastelColor
import styles.YellowPastelColor

@OptIn(ExperimentalResourceApi::class)
enum class TestStatus(val res: StringResource, val color: Color) {
    Assessed(res = Res.string.test_status_assessed, color = GreenPastelColor),
    Await(res = Res.string.test_status_await, color = YellowPastelColor),
    Missed(res = Res.string.test_status_missed, color = RedPastelColor),
}
