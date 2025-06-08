package com.testautomation
import kotlinx.coroutines.runBlocking
import config.Config
import ApiCreateService.ApiCreateUserService
import ApiGetUserService.ApiGetUserDetailService
import ApiGetAllService.ApiUsersService
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main() = runBlocking {
    logger.info { "Iniciando ejecución automática de pruebas API Users" }
    logger.info { "------------------------------------" }
    logger.info { "Test 1: Obtener lista de usuarios" }
    testGetAllUsers()
    logger.info { "------------------------------------" }
    logger.info { "Test 2: Obtener el detalle de un usuario" }
    testGetUserById(Config.id)
    logger.info { "------------------------------------" }
    logger.info { "Test 3: Crear un nuevo usuario" }
    testCreateUser()
    logger.info { "------------------------------------" }
    logger.info { "Ejecución automática de casos de prueba finalizada." }
}

suspend fun testGetAllUsers() {
    val api = ApiUsersService()
    try {
        val users = api.getUsers()
        users.forEach { user ->
            logger.info {
                "Usuario: ID=${user.id}, Nombre=${user.name}, Email=${user.email}, Género=${user.gender}, Estado=${user.status}"
            }
        }
    } catch (e: Exception) {
        logger.error { "Error al obtener usuarios: ${e.message}" }
    }
    
}

suspend fun testGetUserById(id: Int?) {
    val api = ApiGetUserDetailService()
    try {
        if (id == null) {
            logger.warn { "ID inválido: no es posible obtener un usuario sin un ID." }
            return
        }
        val user = api.getUserDetails(id)
        logger.info {
            "Usuario obtenido: ID=${user.id}, Nombre=${user.name}, Email=${user.email}, Género=${user.gender}, Estado=${user.status}"
        }
    } catch (e: Exception) {
        logger.error { "Error al obtener usuario por ID: ${e.message}" }
    }
    
}

suspend fun testCreateUser() {
    val api = ApiCreateUserService()
    try {
        val user = api.createUser()
        logger.info {
            "Usuario creado: ID=${user.id}, Nombre=${user.name}, Email=${user.email}, Género=${user.gender}, Estado=${user.status}"
        }
    } catch (e: Exception) {
        logger.error { "Error al crear usuario: ${e.message}" }
    }
    
}