plugins {
    kotlin("multiplatform") version "1.9.24" apply false
    kotlin("plugin.serialization") version "1.9.22" apply false

    id("app.cash.sqldelight") version "2.0.2" apply false

    id("com.android.library") version "8.2.0" apply false
    id("com.android.application") version "8.2.0" apply false
}
