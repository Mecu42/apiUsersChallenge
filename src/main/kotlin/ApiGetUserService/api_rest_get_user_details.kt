package ApiGetUserService

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import config.Config
import io.ktor.http.contentType
import mu.KotlinLogging
import model.UserResponse

private val logger = KotlinLogging.logger {}

/** Excepción personalizada para el error 404 */
class ResourceNotFoundException(message: String) : Exception(message)

class ApiGetUserDetailService {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    suspend fun getUserDetails(id: Int): UserResponse {
        val endpoint = "${Config.url}/$id"
        logger.info { "Solicitando detalles del usuario desde: $endpoint" }

        try {
            val response: HttpResponse = client.get(endpoint) {
                header(HttpHeaders.Accept, ContentType.Application.Json)
            }

            
            if (response.status == HttpStatusCode.NotFound) {
                val errorBody = response.bodyAsText()
                logger.error { "Error 404: Recurso no encontrado. Body: $errorBody" }
                throw ResourceNotFoundException("Error 404: Recurso no encontrado para el usuario con ID $id")
            } else if (!response.status.isSuccess()) {
                val errorBody = response.bodyAsText()
                logger.error { "Error en la respuesta: ${response.status}. Body: $errorBody" }
                throw ResponseException(response, "Error HTTP ${response.status.value}: $errorBody")
            }

            val user = response.body<UserResponse>()
            logger.info { "Detalles del usuario recibidos: $user" }
            return user

        } catch (e: ResourceNotFoundException) {
            logger.error { "Error 404 capturado: ${e.message}" }
            throw e
        } catch (e: ClientRequestException) {
            logger.error { "Error en la solicitud al obtener detalles del usuario: ${e.message}" }
            throw e
        } catch (e: ServerResponseException) {
            logger.error { "Error en el servidor al obtener detalles del usuario: ${e.message}" }
            throw e
        } catch (e: Exception) {
            logger.error { "Error inesperado al obtener detalles del usuario: ${e.message}" }
            throw e
        }
    }

    suspend fun close() {
        client.close()
        logger.info { "Cliente HTTP cerrado." }
    }
}