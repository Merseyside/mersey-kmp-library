package com.merseyside.merseyLib.utils.core.mappers

import com.merseyside.utils.reflection.ReflectionUtils
import com.merseyside.merseyLib.utils.core.mappers.annotations.InternalMappersApi
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
actual abstract class Mapper<T> actual constructor(actual val qualifier: String) {

    @InternalMappersApi
    actual open fun <R : Any> map(
        value: Any,
        clazz: KClass<R>,
        parametersHolder: ParametersHolder
    ): R {
        return map(value as T, parametersHolder) as R
    }

    actual abstract fun <R> map(value: T, parametersHolder: ParametersHolder): R

    internal val paramType by lazy {
        ReflectionUtils.getGenericParameterClass(
            this.javaClass,
            Mapper::class.java,
            0
        )
    }
}

@Suppress("UNCHECKED_CAST")
actual class CompositeMapper<T> actual constructor(qualifier: String) : Mapper<T>(qualifier) {

    @InternalMappersApi
    val mappers: MutableMap<Class<*>, Mapper<T>> = mutableMapOf()

    @OptIn(InternalMappersApi::class)
    actual inline fun <reified R> addMapper(mapper: Mapper<T>) {
        val clazz = R::class.java
        if (findResponsibleFor(clazz) == null) mappers[clazz] = mapper
    }

    @InternalMappersApi
    fun <R> findResponsibleFor(clazz: Class<R>): Mapper<T>? {
        val foundKey = mappers.keys.find { key -> key == clazz }
        return mappers[foundKey]
    }

    @InternalMappersApi
    override fun <R : Any> map(
        value: Any,
        clazz: KClass<R>,
        parametersHolder: ParametersHolder
    ): R {
        val responsibleMapper = requireNotNull(findResponsibleFor(clazz.java)) {
            "Responsible mapper for $paramType(in) and $clazz(out) not found"
        }

        return responsibleMapper.map(value as T, parametersHolder)
    }

    final override fun <R> map(value: T, parametersHolder: ParametersHolder): R {
        throw UnsupportedOperationException()
    }
}