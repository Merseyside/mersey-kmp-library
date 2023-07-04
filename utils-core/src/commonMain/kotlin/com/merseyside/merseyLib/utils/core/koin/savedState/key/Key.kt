package com.merseyside.merseyLib.utils.core.koin.savedState.key

import kotlin.reflect.KClass

fun getStateKey(clazz: KClass<*>): String {
    return "${clazz.simpleName}_state_key"
}

inline fun <reified T> getStateKey(): String {
    return "${T::class.simpleName}_state_key"
}