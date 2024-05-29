import android.os.Build

class AndroidPlatform : Platform {
    override val platform: Platforms = Platforms.android
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()