package com.merseyside.merseyLib.utils.core.koin.multibinding.map

import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

inline fun <reified Key : Any, reified T> Module.retainedFactory(
    qualifier: Qualifier = multibindingMapQualifier<Key, T>(),
    noinline provide: Scope.(key: Key) -> T
): KoinDefinition<T> {
    declareMultibinding<Key, T>()
    return factory {
        val multibinding = getMultibinding<Key, T>(qualifier)
        val key = get<Key>()
        multibinding[get()] ?: run {
            val newValue = provide(key)
            getMutableMultibinding<Key, T>(qualifier)[key] = newValue
            newValue
        }
    }

}