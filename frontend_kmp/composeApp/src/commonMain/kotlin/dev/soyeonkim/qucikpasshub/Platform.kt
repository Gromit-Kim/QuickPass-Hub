package dev.soyeonkim.qucikpasshub

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform