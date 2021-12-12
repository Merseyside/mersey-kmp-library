package com.merseyside.merseyLib.utils.core.ext

fun <T> List<T>.merge(vararg lists: List<T>): List<T> {
    if (lists.isEmpty()) throw IllegalArgumentException("Pass at least one list!")
    val list: MutableList<T> = ArrayList(this)

    lists.forEach {
        list.addAll(it)
    }

    return list
}