package com.merseyside.merseyLib.utils.core.koin.scope.dsl

import com.merseyside.merseyLib.kotlin.coroutines.utils.defaultDispatcher
import com.merseyside.merseyLib.kotlin.coroutines.utils.uiDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.ScopeDSL
import org.koin.dsl.onClose

fun ScopeDSL.coroutineScope(
    closeScope: Boolean = true,
    qualifier: Qualifier? = null,
    definition: Definition<CoroutineScope>
): KoinDefinition<CoroutineScope> {
    return scoped(qualifier, definition) onClose { coroutine -> if (closeScope) coroutine?.cancel() }
}

fun ScopeDSL.defaultCoroutineScope(closeScope: Boolean = true, qualifier: Qualifier? = null): KoinDefinition<CoroutineScope> {
    return coroutineScope(closeScope, qualifier) { CoroutineScope(defaultDispatcher) }
}

fun ScopeDSL.uiCoroutineScope(closeScope: Boolean = true, qualifier: Qualifier? = null): KoinDefinition<CoroutineScope> {
    return coroutineScope(closeScope, qualifier) { CoroutineScope(uiDispatcher) }
}