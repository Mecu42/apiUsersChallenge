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
import kotlinx.serialization.decodeFromString
import config.Config

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)

class ApiUsersService {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(json)
        }
    }

    suspend fun getUsers(): List<User> {
        val response: HttpResponse = client.get(Config.url)
        val rawJson = response.bodyAsText()
        return json.decodeFromString(rawJson)
    }

    suspend fun close() {
        client.close()
    }
}

