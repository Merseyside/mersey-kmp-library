package com.merseyside.merseyLib.utils.core.mappers

import com.merseyside.merseyLib.utils.core.mappers.annotations.InternalMappersApi
import kotlin.reflect.KClass

expect abstract class Mapper<T> constructor(qualifier: Qualifier) {

    val qualifier: Qualifier

    @InternalMappersApi
    open fun <R : Any> map(value: Any, clazz: KClass<R>, parametersHolder: ParametersHolder): R

    abstract fun <R> map(value: T, parametersHolder: ParametersHolder): R
}

expect class CompositeMapper<T> (qualifier: Qualifier) : Mapper<T> {
    inline fun <reified R> addMapper(mapper: Mapper<T>)
}

