import kotlinx.datetime.Instant
import users.User

data class Test(
    val creator: User,
    val name: String,
    val course: Course,
    val start_at: Instant,
    val end_at: Instant,
    val questions: List<Question>,
)