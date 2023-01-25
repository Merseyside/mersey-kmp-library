package com.merseyside.merseyLib.utils.core.koin.scope.dsl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.ScopeDSL
import org.koin.dsl.onClose

inline fun <reified T : CoroutineScope> ScopeDSL.coroutineScope(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return scoped(qualifier, definition) onClose { coroutine -> coroutine?.cancel() }
}