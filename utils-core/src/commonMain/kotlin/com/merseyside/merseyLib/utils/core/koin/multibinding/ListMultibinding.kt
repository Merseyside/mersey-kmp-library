package com.merseyside.merseyLib.utils.core.koin.multibinding

import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.onClose
import org.koin.ext.getFullName
import kotlin.reflect.KClass

class ListMultibinding<V> : MutableList<V> by mutableListOf()

inline fun <reified V : Any> Koin.getList(qualifier: Qualifier? = null): List<V> {
    return get<ListMultibinding<V>>(qualifier ?: getMultibindingListQualifier(V::class))
}

inline fun <reified V : Any> Koin.getListOrNull(qualifier: Qualifier? = null): List<V>? {
    return getOrNull<ListMultibinding<V>>(qualifier ?: getMultibindingListQualifier(V::class))
}

inline fun <reified V : Any> Scope.getList(qualifier: Qualifier? = null): List<V> {
    return get<ListMultibinding<V>>(qualifier ?: getMultibindingListQualifier(V::class))
}

inline fun <reified V : Any> Scope.getListOrNull(
    qualifier: Qualifier = getMultibindingListQualifier(
        V::class
    )
): List<V>? {
    return getOrNull<ListMultibinding<V>>(qualifier)
}

inline fun <reified V : Any, reified T : Any> Module.intoList(
    qualifier: Qualifier = getMultibindingListQualifier(V::class),
    crossinline definition: Scope.() -> V
) {
    intoList(qualifier, named(T::class.getFullName()), definition)
}

inline fun <reified V : Any> Module.intoList(
    qualifier: Qualifier = getMultibindingListQualifier(V::class),
    elemQualifier: Qualifier,
    crossinline definition: Scope.() -> V
) {
    var multibinding: ListMultibinding<V>? = null
    var element: V? = null
    single(
        qualifier = named("${qualifier.value}_${elemQualifier.value}"),
        createdAtStart = true
    ) {
        get<ListMultibinding<V>>(qualifier).let { list ->
            val element: V = definition()
            list.add(element)
            multibinding = list
        }
    }.onClose { multibinding?.remove(element) }
}

inline fun <reified V : Any> Module.declareList(qualifier: Qualifier? = null) {
    single(qualifier ?: getMultibindingListQualifier(V::class)) { ListMultibinding<V>() }
}

fun <V : Any> getMultibindingListQualifier(clazz: KClass<V>): Qualifier {
    return named(getMultibindingListName<V>(clazz))
}

fun <V : Any> getMultibindingListName(clazz: KClass<V>): String {
    return clazz.getFullName()
}