package users

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val type: Int, // UserType
    val name: String,
    val email: String
)
