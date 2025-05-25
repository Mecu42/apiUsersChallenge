package com.testautomation
import kotlinx.coroutines.runBlocking
import java.io.FileInputStream
import java.util.Properties
import config.Config
import ApiCreateService.ApiCreateUserService
import ApiGetUserService.ApiGetUserDetailService
import ApiGetAllService.ApiUsersService
import kotlinx.serialization.Serializable

fun main() = runBlocking {
    println("Iniciando ejecucion automatica de pruebas API Users")
    println("------------------------------------")
    println("Test 1: Obtener lista de usuarios")
    testGetAllUsers()
    println("------------------------------------")
    println("Test 2: Obtener el detalle de un usuario")
    testGetUserById(Config.id)
    println("------------------------------------")
    println("Test 3: Crear un nuevo usuario")
    testCreateUser()
    println("------------------------------------")
    println("Ejecucion automatica de Casos de Prueba finalizada.")
}
suspend fun testGetAllUsers() {
    val api = ApiUsersService()
    try {
        val users = api.getUsers()
        users.forEach { user ->
            println("Usuario: ID=${user.id}, Nombre=${user.name}, Email=${user.email}, Género=${user.gender}, Estado=${user.status}")
        }
    } catch (e: Exception) {
        println("Error al obtener usuarios: ${e.message}")
    } finally {
        api.close()
    }
}

suspend fun testGetUserById(id: Int) {
    val api = ApiGetUserDetailService()
    try {
        val user = api.getUserDetails(id)
        println("Usuario obtenido: ID=${user.id}, Nombre=${user.name}, Email=${user.email}, Género=${user.gender}, Estado=${user.status}")
    } catch (e: Exception) {
        println("Error al obtener usuario por ID: ${e.message}")
    } finally {
        api.close()
    }
}
suspend fun testCreateUser() {
    val api = ApiCreateUserService()

    try {
        val user = api.createUser()
        println("Usuario creado: ID=${user.id}, Nombre=${user.name}, Email=${user.email}, Género=${user.gender}, Estado=${user.status}")
    } catch (e: Exception) {
        println("Error al crear usuario: ${e.message}")
    } finally {
        api.close()
    }
}
    