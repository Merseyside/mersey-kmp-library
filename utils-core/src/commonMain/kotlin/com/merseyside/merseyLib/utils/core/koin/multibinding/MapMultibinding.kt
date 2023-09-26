@file:OptIn(KoinInternalApi::class)

package com.merseyside.merseyLib.utils.core.koin.multibinding

import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.onClose
import org.koin.ext.getFullName

class MapMultibinding<K, V> : MutableMap<K, V> by mutableMapOf()

inline fun <reified K, reified V> Koin.getMultibindingMap(qualifier: Qualifier = multibindingQualifier<K, V>()): Map<K, V> =
    get<MapMultibinding<K, V>>(qualifier).toMap()

inline fun <reified K, reified V> Koin.getOrNullMultibindingMap(qualifier: Qualifier = multibindingQualifier<K, V>()): Map<K, V>? {
    return getOrNull<Map<K, V>>(qualifier)?.toMap()
}

/* Scope */
inline fun <reified K, reified V> Scope.getOrNullMultibindingMap(qualifier: Qualifier = multibindingQualifier<K, V>()): Map<K, V>? =
    getOrNull<MapMultibinding<K, V>>(qualifier)?.toMap()

inline fun <reified K, reified V> Module.intoMultibinding(
    key: K,
    value: V,
    qualifier: Qualifier = multibindingQualifier<K, V>(),
) {
    var multibinding: MapMultibinding<K, V>? = null
    single(
        qualifier = named("${qualifier.value}_$key"),
        createdAtStart = true,
    ) {
        get<MapMultibinding<K, V>>(qualifier).let { map ->
            map[key] = value
            multibinding = map
        }
    }.onClose { multibinding?.remove(key) }
}

inline fun <reified K, reified V> Module.declareMultibinding() {
    declareMultibinding<K, V>(multibindingQualifier<K, V>())
}

inline fun <reified K, reified V> Module.declareMultibinding(qualifier: Qualifier = multibindingQualifier<K, V>()) {
    single(qualifier) { MapMultibinding<K, V>() }
}


@KoinInternalApi
inline fun <reified K, reified V> multibindingQualifier(): Qualifier =
    named(multibindingName<K, V>())


@KoinInternalApi
inline fun <reified K, reified V> multibindingName(): String =
    "${K::class.getFullName()}_${V::class.getFullName()}"