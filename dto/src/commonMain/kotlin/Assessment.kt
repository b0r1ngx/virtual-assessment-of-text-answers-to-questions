import kotlinx.serialization.Serializable

// todo: thinks that sends usermodels is exhaustive, may just send emails
@Serializable
data class Assessment(
    val testId: Long,
    val teacherEmail: String,
    val studentEmail: String,
    val text: String,
    val mark: Double,
)