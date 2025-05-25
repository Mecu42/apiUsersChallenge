package ApiCreateService

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import config.Config

@Serializable
data class UserRequest(
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)

class ApiCreateUserService {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    suspend fun createUser(): UserResponse {
        val userRequest = UserRequest(
            name = Config.name,
            email = Config.email,
            gender = Config.gender,
            status = Config.status
        )

        println("Enviando usuario: $userRequest")

        try {
            val response: HttpResponse = client.post(Config.url) {
                header("Authorization", "Bearer ${Config.token}")
                header("Accept", "application/json")
                contentType(ContentType.Application.Json)
                setBody(userRequest)
            }

            val responseBody = response.bodyAsText()
            println("Respuesta HTTP: ${response.status}")
            println("Response Body: $responseBody")

            if (response.status.isSuccess()) {
                return Json.decodeFromString(responseBody)
            } else {
                throw Exception("Error HTTP ${response.status.value}: $responseBody")
            }

        } catch (e: Exception) {
            println("Error al crear usuario: ${e.message}")
            throw e
        }
    }

    suspend fun close() {
        client.close()
    }
}
