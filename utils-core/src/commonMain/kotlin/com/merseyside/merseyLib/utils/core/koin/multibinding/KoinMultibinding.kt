package com.merseyside.merseyLib.utils.core.koin.multibinding

import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.onClose
import org.koin.ext.getFullName

class Multibinding<K, V> : MutableMap<K, V> by mutableMapOf()

inline fun <reified K, reified V> Koin.getOrNullMultibinding(qualifier: Qualifier = multibindingQualifier<K, V>()): Map<K, V>? {
    return getOrNull<Map<K, V>>(qualifier)?.toMap()
}

inline fun <reified K, reified V> Koin.getMultibinding(qualifier: Qualifier = multibindingQualifier<K, V>()): Map<K, V> =
    get<Multibinding<K, V>>(qualifier).toMap()

inline fun <reified K, reified V> Koin.getMultibinding(): Map<K, V> =
    getMultibinding(multibindingQualifier<K, V>())

inline fun <reified K, reified V> Koin.getMultibindingList(): List<V> =
    getMultibinding<K, V>().values.toList()

inline fun <reified K, reified V> Koin.getMultibindingList(qualifier: Qualifier = multibindingQualifier<K, V>()): List<V> =
    getMultibinding<K, V>(qualifier).values.toList()

/* Scope */
inline fun <reified K, reified V> Scope.getOrNullMultibinding(qualifier: Qualifier = multibindingQualifier<K, V>()): Map<K, V>? =
    getOrNull<Multibinding<K, V>>(qualifier)?.toMap()

inline fun <reified K, reified V> Scope.getMultibinding(qualifier: Qualifier = multibindingQualifier<K, V>()): Map<K, V> =
    getMutableMultibinding<K, V>(qualifier).toMap()

inline fun <reified K, reified V> Scope.getMutableMultibinding(qualifier: Qualifier = multibindingQualifier<K, V>()): MutableMap<K, V> =
    get<Multibinding<K, V>>(qualifier)

inline fun <reified K, reified V> Scope.getMultibinding(): Map<K, V> =
    getMultibinding(multibindingQualifier<K, V>())

inline fun <reified K, reified V> Scope.getMultibindingList(): List<V> =
    getMultibinding<K, V>().values.toList()

inline fun <reified K, reified V> Scope.getMultibindingList(qualifier: Qualifier = multibindingQualifier<K, V>()): List<V> =
    getMultibinding<K, V>(qualifier).values.toList()

inline fun <reified K, reified V> Module.intoMultibinding(
    qualifier: Qualifier = multibindingQualifier<K, V>(),
    key: K,
    value: V
) {
    var multibinding: Multibinding<K, V>? = null
    single(
        qualifier = named("${qualifier.value}_$key"),
        createdAtStart = true,
    ) {
        get<Multibinding<K, V>>(qualifier).let {
            it[key] = value
            multibinding = it
        }
    }.onClose {
        multibinding?.remove(key)
    }
}

inline fun <reified K, reified V> Module.intoMultibinding(key: K, value: V) {
    intoMultibinding(multibindingQualifier<K, V>(), key, value)
}

inline fun <reified K, reified V> Module.declareMultibinding() {
    declareMultibinding<K, V>(multibindingQualifier<K, V>())
}

inline fun <reified K, reified V> Module.declareMultibinding(qualifier: Qualifier = multibindingQualifier<K, V>()) {
    single(qualifier) { Multibinding<K, V>() }
}


inline fun <reified K, reified V> multibindingQualifier(): Qualifier =
    named(multibindingName<K, V>())

inline fun <reified V> multibindingQualifier(qualifier: Qualifier): Qualifier =
    named(multibindingName<V>(qualifier))

inline fun <reified K, reified V> multibindingName(): String =
    "${K::class.getFullName()}_${V::class.getFullName()}"

inline fun <reified V> multibindingName(qualifier: Qualifier): String =
    "${qualifier.value}_${V::class.getFullName()}"