package com.merseyside.merseyLib.utils.core.koin.scope.dsl

import kotlinx.coroutines.CoroutineScope
import org.koin.core.definition.KoinDefinition
import org.koin.dsl.ScopeDSL

inline fun <reified T : CoroutineScope> ScopeDSL.coroutineScope(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return scoped(qualifier, definition).onClose { it?.cancel() }
}