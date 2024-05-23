import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class TestModel(
    val creator: User,
    val name: String,
    val course: Course,
    val start_at: Instant,
    val end_at: Instant,
    val questions: List<Question>,
)