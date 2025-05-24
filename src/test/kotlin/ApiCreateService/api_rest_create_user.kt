package ApiCreateService

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import config.Config
import io.ktor.client.request.forms.*
import io.ktor.http.contentType



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
    val response: HttpResponse = client.post(Config.url) {
        header("Authorization", "Bearer ${Config.token}")
        contentType(io.ktor.http.ContentType.Application.Json)
        setBody(
            UserRequest(
                name = Config.name,
                email = Config.email,
                gender = Config.gender,
                status = Config.status
            )
        )
    }
    return response.body()
}

suspend fun close() {
        client.close()
    }
}

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