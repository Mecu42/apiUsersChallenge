plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.serialization") version "2.1.21" 
    application
}

tasks.register("listTasks") {
    doLast {
        println(tasks.names)
    }
}

application {
    mainClass.set("com.testautomation.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-cio:2.3.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1") 
    implementation("io.github.microutils:kotlin-logging:2.1.21")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

sourceSets {
    main {
        java.srcDirs("src/main/kotlin")
    }
    test {
        java.srcDirs("src/test/kotlin")
    }
}
