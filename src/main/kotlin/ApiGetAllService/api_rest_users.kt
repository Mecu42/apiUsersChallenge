package ApiGetAllService

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import config.Config
import model.UserResponse

private val logger = KotlinLogging.logger {}

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)

class ApiUsersService {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    suspend fun getUsers(): List<User> {
        return try {
            client.use { httpClient ->
                val response: HttpResponse = httpClient.get(Config.url) {
                    header("Accept", "application/json")
                }

                logger.info { "Respuesta HTTP: ${response.status}" }

                if (response.status.isSuccess()) {
                    response.body<List<User>>()
                } else {
                    throw ResponseException(
                        response,
                        "Error HTTP ${response.status.value}: ${response.bodyAsText()}"
                    )
                }
            }
        } catch (e: ClientRequestException) {
            logger.error { "Error en la solicitud: ${e.message}" }
            throw e
        } catch (e: ServerResponseException) {
            logger.error { "Error en el servidor: ${e.message}" }
            throw e
        } catch (e: Exception) {
            logger.error { "Error inesperado: ${e.message}" }
            throw e
        }
    }
}