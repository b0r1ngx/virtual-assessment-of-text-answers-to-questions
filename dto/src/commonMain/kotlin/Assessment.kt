import kotlinx.serialization.Serializable

// todo: thinks that sends usermodels is exhaustive, may just send emails
@Serializable
data class Assessment(
    val testId: Long,
    val teacher: UserModel,
    val student: UserModel,
    val text: String,
    val mark: Double,
)