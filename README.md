# Acerca del Proyecto 
El proyecto de automatizacion de las pruebas de la API rest USers se desarrolló en Kotlin, utilizando Gradle para la compilacion y ejecución de los tests.

# Estructura del Proyecto
- `src/main/kotlin`: Contiene los archivos de los 3 casos de prueba: ApiCreateService, ApiGetAllService, ApiGetUserService
- `src/main/resources`: Contiene el archivo de configuracion config.properties que contiene los datos input para los tests
- `Challenge_Ejercicio_Backend.docx`: contiene el documento con la estrategia de pruebas implementada para esta solicitud.

# Instalacion y configuracion preliminar
    Java
    Gradle como compilador y ejecutor de pruebas 
    Configuracion de variables de entorno: GRADLE_HOME, PATH: Kotlin\bin, gradle-8.14.1\bin
    
# Ejecucion del proyecto
Para ejecutar el test desde la linea de comandos en Terminal:
./gradlew clean build       //utilizado para compilar el proyecto antes de ejecutar
./gradlew run               //utilizado para ejecutar los tests


