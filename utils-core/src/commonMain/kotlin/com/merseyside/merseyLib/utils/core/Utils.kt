package com.merseyside.merseyLib.utils.core

fun generateRandomString(length: Int = 10): String {
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    return (1..length)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}

fun String.hexStringToByteArray() = ByteArray(this.length / 2) { this.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

fun <T> merge(first: List<T>, second: List<T>): List<T> {
    val list: MutableList<T> = ArrayList(first)
    list.addAll(second)
    return list
}