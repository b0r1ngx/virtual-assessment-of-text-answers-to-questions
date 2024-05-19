package screens.utils

import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.questions_one
import dev.boringx.compose.generated.resources.questions_few
import dev.boringx.compose.generated.resources.questions_many
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalResourceApi::class)
private val defaultStrings: List<StringResource> = listOf(
    Res.string.questions_one, Res.string.questions_few, Res.string.questions_many
)
private val fewRange = 2..4

@OptIn(ExperimentalResourceApi::class)
fun choosePlural(amount: Int, strings: List<StringResource> = defaultStrings): StringResource {
    return when {
        amount % 10 == 1 && amount % 100 != 11 -> strings[0]
        fewRange.contains(amount % 10) && (amount % 100 < 10 || amount % 100 >= 20) -> strings[1]
        else -> strings[2]
    }
}