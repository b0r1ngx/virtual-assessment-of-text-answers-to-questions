// TODO: After some point, try to use guide explained here https://github.com/ktorio/ktor-documentation/tree/2.3.10/codeSnippets/snippets/embedded-server-native
//  to run a Ktor server in a Kotlin/Native application (not via JVM)
//  also check: https://ktor.io/docs/server-native.html#native-target
plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
    application
}

group = "dev.boringx"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("api.server.mainKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":dto"))

    implementation(libs.dotenv)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.kotlinx.serialization)
    implementation(libs.ktor.serialization.kotlinx.json)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotlin.test)
    // TODO Warning: Below dependency, raises warning: Provides transitive vulnerable dependency maven:commons-codec:commons-codec:1.11 Cxeb68d52e-5509 3.7 Exposure of Sensitive Information to an Unauthorized Actor vulnerability with Low severity found  Results powered by Checkmarx(c) https://devhub.checkmarx.com/cve-details/Cxeb68d52e-5509/
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.ktor.server.test.host)
}
