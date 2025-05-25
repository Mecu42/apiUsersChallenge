package config

import java.util.Properties

object Config {
    private val props: Properties = Properties()

    init {
        val inputStream = Config::class.java.classLoader.getResourceAsStream("config.properties")
            ?: throw IllegalStateException("No se encontró el archivo config.properties en resources.")
        props.load(inputStream)
    }

    val name: String get() = props.getProperty("name")
    val email: String get() = props.getProperty("email")
    val gender: String get() = props.getProperty("gender")
    val status: String get() = props.getProperty("status")
    val url: String get() = props.getProperty("url")
    val id: Int get() = props.getProperty("id").toInt()
    val token: String get() = props.getProperty("token")
}
