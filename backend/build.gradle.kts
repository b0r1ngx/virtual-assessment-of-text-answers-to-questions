plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
    // alias(libs.plugins.ktor) is it needed here?
}

group = "dev.boringx"
version = "1.0-SNAPSHOT"
//application {
//    mainClass.set("dev.boringx.va.ApplicationKt")
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
//}

// TODO: After some point, try to use guide explained here https://github.com/ktorio/ktor-documentation/tree/2.3.10/codeSnippets/snippets/embedded-server-native
//  to run a Ktor server in a Kotlin/Native application (not via JVM ?)
kotlin {
    jvmToolchain(20)
    jvm()

    sourceSets {
        jvmMain.dependencies {
            // don't understand to the end, what sqldelight implementation to use here...
            // below dependency (native) on macOS indicates error
//    implementation("app.cash.sqldelight:native-driver:2.0.2")
            implementation(libs.ktor.core)
            implementation(libs.ktor.netty)
            implementation(libs.kotlinx.serialization)
            implementation(libs.dotenv)
        }

        jvmTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ktor.tests)
            // TODO Warning: Below dependency, raises warning: Provides transitive vulnerable dependency maven:commons-codec:commons-codec:1.11 Cxeb68d52e-5509 3.7 Exposure of Sensitive Information to an Unauthorized Actor vulnerability with Low severity found  Results powered by Checkmarx(c) https://devhub.checkmarx.com/cve-details/Cxeb68d52e-5509/
            implementation(libs.ktor.test.host)
        }
    }
}
