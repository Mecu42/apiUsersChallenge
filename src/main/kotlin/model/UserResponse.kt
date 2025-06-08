// src/main/kotlin/model/UserResponse.kt
package model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)