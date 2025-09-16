package dev.syk.quickpasshub

import dev.syk.quickpasshub.core.Platform

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()