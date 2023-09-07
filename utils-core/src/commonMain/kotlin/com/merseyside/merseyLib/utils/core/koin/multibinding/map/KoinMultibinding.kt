package com.merseyside.merseyLib.utils.core.koin.multibinding.map

import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.onClose
import org.koin.ext.getFullName

class MultibindingMap<K, V> : MutableMap<K, V> by mutableMapOf()

inline fun <reified K, reified V> Koin.getOrNullMultibinding(qualifier: Qualifier = multibindingMapQualifier<K, V>()): Map<K, V>? {
    return getOrNull<Map<K, V>>(qualifier)?.toMap()
}

inline fun <reified K, reified V> Koin.getMultibinding(qualifier: Qualifier = multibindingMapQualifier<K, V>()): Map<K, V> =
    get<MultibindingMap<K, V>>(qualifier).toMap()

inline fun <reified K, reified V> Koin.getMultibinding(): Map<K, V> =
    getMultibinding(multibindingMapQualifier<K, V>())

inline fun <reified K, reified V> Koin.getMultibindingList(): List<V> =
    getMultibinding<K, V>().values.toList()

inline fun <reified K, reified V> Koin.getMultibindingList(qualifier: Qualifier = multibindingMapQualifier<K, V>()): List<V> =
    getMultibinding<K, V>(qualifier).values.toList()

/* Scope */

inline fun <reified K, reified V> Scope.getOrNullMultibinding(qualifier: Qualifier = multibindingMapQualifier<K, V>()): Map<K, V>? =
    getOrNull<MultibindingMap<K, V>>(qualifier)?.toMap()

inline fun <reified K, reified V> Scope.getMultibinding(qualifier: Qualifier = multibindingMapQualifier<K, V>()): Map<K, V> =
    getMutableMultibinding<K, V>(qualifier).toMap()

inline fun <reified K, reified V> Scope.getMutableMultibinding(qualifier: Qualifier = multibindingMapQualifier<K, V>()): MutableMap<K, V> =
    get<MultibindingMap<K, V>>(qualifier)

inline fun <reified K, reified V> Scope.getMultibinding(): Map<K, V> =
    getMultibinding(multibindingMapQualifier<K, V>())

inline fun <reified K, reified V> Scope.getMultibindingList(): List<V> =
    getMultibinding<K, V>().values.toList()

inline fun <reified K, reified V> Scope.getMultibindingList(qualifier: Qualifier = multibindingMapQualifier<K, V>()): List<V> =
    getMultibinding<K, V>(qualifier).values.toList()

inline fun <reified K, reified V> Module.intoMultibinding(
    qualifier: Qualifier = multibindingMapQualifier<K, V>(),
    key: K,
    value: V
) {
    var multibindingMap: MultibindingMap<K, V>? = null
    single(
        qualifier = named("${qualifier.value}_$key"),
        createdAtStart = true
    ) {
        get<MultibindingMap<K, V>>(qualifier).let {
            it[key] = value
            multibindingMap = it
        }
    }.onClose {
        multibindingMap?.remove(key)
    }
}

inline fun <reified K, reified V> Module.intoMultibinding(key: K, value: V) {
    intoMultibinding(multibindingMapQualifier<K, V>(), key, value)
}

inline fun <reified K, reified V> Module.declareMultibinding() {
    declareMultibinding<K, V>(multibindingMapQualifier<K, V>())
}

inline fun <reified K, reified V> Module.declareMultibinding(qualifier: Qualifier = multibindingMapQualifier<K, V>()) {
    single(qualifier) { MultibindingMap<K, V>() }
}


inline fun <reified K, reified V> multibindingMapQualifier(): Qualifier =
    named(multibindingMapName<K, V>())

inline fun <reified V> multibindingMapQualifier(qualifier: Qualifier): Qualifier =
    named(multibindingMapName<V>(qualifier))

inline fun <reified K, reified V> multibindingMapName(): String =
    "${K::class.getFullName()}_${V::class.getFullName()}"

inline fun <reified V> multibindingMapName(qualifier: Qualifier): String =
    "${qualifier.value}_${V::class.getFullName()}"