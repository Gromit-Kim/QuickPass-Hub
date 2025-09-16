package dev.syk.quickpasshub.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform