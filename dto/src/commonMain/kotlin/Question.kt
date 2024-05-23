import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Long = -1,
    val text: String
)
