package com.merseyside.merseyLib.utils.core.mappers

import com.merseyside.merseyLib.utils.core.mappers.annotations.InternalMappersApi
import kotlin.reflect.KClass

actual abstract class Mapper<T> actual constructor(actual val qualifier: String) {

    @InternalMappersApi
    actual open fun <R : Any> map(value: Any, clazz: KClass<R>, parametersHolder: ParametersHolder): R {
        TODO()
    }

    actual abstract fun <R> map(value: T, parametersHolder: ParametersHolder): R
}

actual class CompositeMapper<T> actual constructor (qualifier: String) : Mapper<T>(qualifier) {

    @InternalMappersApi
    val mappers: MutableMap<KClass<*>, Mapper<T>> = mutableMapOf()

    @OptIn(InternalMappersApi::class)
    actual inline fun <reified R> addMapper(mapper: Mapper<T>) {
        TODO()
    }

    @InternalMappersApi
    fun <R : Any> findResponsibleFor(clazz: KClass<R>): Mapper<T>? {
        TODO()
    }

    override fun <R> map(value: T, parametersHolder: ParametersHolder): R {
        TODO("Not yet implemented")
    }
}