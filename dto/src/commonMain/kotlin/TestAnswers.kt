import kotlinx.serialization.Serializable

@Serializable
data class TestAnswers(
    val testId: Long,
    val user: UserModel,
    val questionsToAnswers: List<Pair<Question, Answer>>,
    val avgMarkAi: Double = -1.0,
)
