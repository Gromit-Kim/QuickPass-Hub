package dev.syk.quickpasshub

import android.os.Build
import dev.syk.quickpasshub.core.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()