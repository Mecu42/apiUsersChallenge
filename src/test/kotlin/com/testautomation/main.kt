package com.testautomation
import kotlinx.coroutines.runBlocking
import java.io.FileInputStream
import java.util.Properties
import config.Config
import ApiCreateService.ApiCreateUserService
import ApiGetUserService.ApiGetUserDetailService
import ApiGetAllService.ApiUsersService

fun main() = runBlocking {
    println("Iniciando ejecución automática de pruebas API Users")

    testGetAllUsers()
    testGetUserById(Config.id)
    testCreateUser()

    println("Ejecución de Pruebas finalizada.")
}
suspend fun testGetAllUsers() {
    val api = ApiUsersService()
    try {
        val user = api.getUsers()
        println("Usuarios obtenidos: ID=${user.id}, Nombre=${user.name}, Email=${user.email}, Género=${user.gender}, Estado=${user.status}")
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
    //val newUser = cargarUsuarioDesdeConfig()

    try {
        val user = api.createUser()
        println("Usuario creado: ID=${user.id}, Nombre=${user.name}, Email=${user.email}, Género=${user.gender}, Estado=${user.status}")
    } catch (e: Exception) {
        println("Error al crear usuario: ${e.message}")
    } finally {
        api.close()
    }
}
    