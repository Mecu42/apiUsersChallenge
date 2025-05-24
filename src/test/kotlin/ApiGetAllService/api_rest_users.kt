package ApiGetAllService

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



class ApiUsersService {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    suspend fun getUsers(): UsersResponse {
        val response: HttpResponse = client.get(Config.url) 
            return response.body()
    }

    suspend fun close() {
        client.close()
    }
}

@Serializable
data class UsersResponse(
    val id: Int,
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)
