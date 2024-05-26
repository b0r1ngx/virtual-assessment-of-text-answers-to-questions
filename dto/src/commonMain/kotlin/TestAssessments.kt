data class TestAssessments(
    val testId: Long,
    val userEmail: String,
    val questionId: Long,
    val answer: Answer,
    val avgMarkAi: Double,
    val criteriaToMarkWithResponse: List<Triple<Criterion, Int, String>>,
)
