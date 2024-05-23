import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: Long,
    val name: String
)
