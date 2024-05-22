package model

import dev.boringx.compose.generated.resources.Res
import dev.boringx.compose.generated.resources.student
import dev.boringx.compose.generated.resources.teacher
import org.jetbrains.compose.resources.StringResource

enum class UserType(val res: StringResource) {
    Student(res = Res.string.student),
    Teacher(res = Res.string.teacher),
}
