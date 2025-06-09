package ApiCreateService

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
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

class UnprocessableEntityException(message: String) : Exception(message)

@Serializable
data class UserRequest(
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
        logger.info { "Enviando usuario: $userRequest" }

        return try {
            client.use { httpClient ->
                val response: HttpResponse = httpClient.post(Config.url) {
                    header("Authorization", "Bearer ${Config.token}")
                    header("Accept", "application/json")
                    contentType(ContentType.Application.Json)
                    setBody(userRequest)
                }
                logger.info { "Respuesta HTTP: ${response.status}" }

                if (response.status == HttpStatusCode.UnprocessableEntity) {
                    val errorBody = response.bodyAsText()
                    logger.error { "Error 422: Unprocessable Entity. Body: $errorBody" }
                    throw UnprocessableEntityException("Error 422: $errorBody")
                } else if (!response.status.isSuccess()) {
                    val errorBody = response.bodyAsText()
                    logger.error { "Error en la respuesta: ${response.status}. Body: $errorBody" }
                    throw ResponseException(response, "Error HTTP ${response.status.value}: $errorBody")
                }

                response.body<UserResponse>()
            }
        } catch (e: ClientRequestException) {
            logger.error { "Error en la solicitud: ${e.message}" }
            throw e
        } catch (e: ServerResponseException) {
            logger.error { "Error en el servidor: ${e.message}" }
            throw e
        } catch (e: UnprocessableEntityException) {
            logger.error { "Error 422 capturado: ${e.message}" }
            throw e
        } catch (e: Exception) {
            logger.error { "Error inesperado: ${e.message}" }
            throw e
        }
    }

    suspend fun close() {
        client.close()
        logger.info { "Cliente HTTP cerrado." }
    }
}