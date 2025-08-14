package dev.syk.quickpasshub

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform