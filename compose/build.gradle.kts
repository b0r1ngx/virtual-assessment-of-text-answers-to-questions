plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

group = "dev.boringx"
version = "1.0-SNAPSHOT"

kotlin {
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
            baseName = "Compose"
            isStatic = true

            export(libs.decompose)
            export(libs.essenty.lifecycle)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":common"))
            implementation(project(":dto"))

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            api(libs.decompose)
            api(libs.essenty.lifecycle)
            implementation(libs.decompose.extensions.compose)
            implementation(libs.essenty.lifecycle.coroutines)
            implementation(libs.kotlinx.serialization)
        }
        androidMain.dependencies {
//            implementation(libs.koin.androidx.compose)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.compose.ui.tooling.preview)
        }
    }
}

android {
    // TODO: extract app_name to some common place?
    namespace = group.toString() + "VirtualAssessment"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = group.toString() + "VirtualAssessment"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = version.toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
