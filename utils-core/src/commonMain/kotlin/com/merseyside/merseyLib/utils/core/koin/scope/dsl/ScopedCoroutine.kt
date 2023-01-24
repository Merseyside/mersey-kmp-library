package com.merseyside.merseyLib.utils.core.koin.scope.dsl

import kotlinx.coroutines.CoroutineScope
import org.koin.core.definition.KoinDefinition
import org.koin.dsl.ScopeDSL

inline fun <reified R : CoroutineScope> ScopeDSL.coroutineScope(crossinline block: () -> R): R {
    val coroutine = block()
    scoped { coroutine }
    return coroutine
}