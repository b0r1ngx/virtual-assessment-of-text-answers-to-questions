plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
}

group = "dev.boringx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// TODO: After some point, try to use guide explained here https://github.com/ktorio/ktor-documentation/tree/2.3.10/codeSnippets/snippets/embedded-server-native
//  to run a Ktor server in a Kotlin/Native application (not via JVM ?)
dependencies {
    implementation("io.ktor:ktor-server-core:2.3.10")
    implementation("io.ktor:ktor-server-netty:2.3.10")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.ktor:ktor-server-test-host:2.3.10")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(20)
}