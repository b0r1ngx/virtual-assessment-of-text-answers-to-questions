plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("app.cash.sqldelight") version "2.0.2"
}

group = "dev.boringx"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

// TODO: After some point, try to use guide explained here https://github.com/ktorio/ktor-documentation/tree/2.3.10/codeSnippets/snippets/embedded-server-native
//  to run a Ktor server in a Kotlin/Native application (not via JVM ?)
dependencies {
    // don't understand to the end, what sqldelight implementation to use here...
    // below dependency (native) on macOS indicates error
//    implementation("app.cash.sqldelight:native-driver:2.0.2")
    implementation("io.ktor:ktor-server-core:2.3.10")
    implementation("io.ktor:ktor-server-netty:2.3.10")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    // TODO Warning: Below dependency, raises warning: Provides transitive vulnerable dependency maven:commons-codec:commons-codec:1.11 Cxeb68d52e-5509 3.7 Exposure of Sensitive Information to an Unauthorized Actor vulnerability with Low severity found  Results powered by Checkmarx(c) https://devhub.checkmarx.com/cve-details/Cxeb68d52e-5509/
    testImplementation("io.ktor:ktor-server-test-host:2.3.10")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(20)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set(group.toString())
        }
    }
}