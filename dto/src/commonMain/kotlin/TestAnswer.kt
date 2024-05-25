import kotlinx.serialization.Serializable

@Serializable
data class TestAnswers(
    val testId: Long,
    val userEmail: String,
    val questionsToAnswers: List<Pair<Question, Answer>>
)
