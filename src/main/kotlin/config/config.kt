package config
import java.io.FileInputStream
import java.util.Properties

object Config {
    private val props: Properties = Properties()

    init {
        FileInputStream("config.properties").use { props.load(it) }
    }

    val name: String get() = props.getProperty("name")
    val email: String get() = props.getProperty("email")
    val gender: String get() = props.getProperty("gender")
    val status: String get() = props.getProperty("status")
    val url: String get() = props.getProperty("url")
    val id: Int get() = props.getProperty("id").toInt()
    val token: String get() = props.getProperty("token")
}
