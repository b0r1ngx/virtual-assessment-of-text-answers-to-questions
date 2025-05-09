plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.android.library)
}

group = "dev.boringx."
version = "1.0-SNAPSHOT"

kotlin {
    // now for Backend, but later must be changed to native things
    // Server goes Unix-style, so use linux target
    jvm()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Common"
            // isStatic = false
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":dto"))
            implementation(libs.kotlinx.datetime)
            implementation(libs.sqldelight.runtime)
        }
        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
        jvmMain.dependencies {
            implementation(libs.sqldelight.jvm.driver)
        }
    }
}

android {
    namespace = "dev.boringx.common"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set(group.toString())
        }
    }
}
