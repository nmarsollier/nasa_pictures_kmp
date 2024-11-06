package com.nmarsollier.nasapictures

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform