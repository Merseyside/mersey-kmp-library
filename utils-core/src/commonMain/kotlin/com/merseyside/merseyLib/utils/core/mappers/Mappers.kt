package com.merseyside.merseyLib.utils.core.mappers

import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.merseyLib.utils.core.mappers.annotations.InternalMappersApi

class Mappers {

    var isLogging: Boolean = false
        private set
    private val mappers: MutableMap<String, Mapper<out Any>> = mutableMapOf()

    fun setLogging(logging: Boolean) {
        isLogging = logging
    }

    fun addMappers(mappers: List<Mapper<out Any>>) {
        mappers.forEach { mapper -> addMapper(mapper) }
    }

    fun addMapper(mapper: Mapper<out Any>) {
        if (!mappers.containsKey(mapper.qualifier)) {
            mappers[mapper.qualifier] = mapper
        } else throw IllegalArgumentException("Mapper with ${mapper.qualifier} tag has been already registered!")
    }

    fun removeMappers(mapperList: List<Mapper<out Any>>) {
        mapperList.forEach { mapper -> removeMapper(mapper) }
    }

    fun removeMapper(mapper: Mapper<out Any>) {
        mappers.remove(mapper.qualifier)
    }

    @OptIn(InternalMappersApi::class)
    inline fun <reified R : Any> mapList(
        iterable: Iterable<out Any>,
        qualifier: Qualifier,
        args: Array<out Any?>
    ): List<R> {
        Logger.logInfo("Mappers", "Mapping $qualifier")
        val responsibleMapper = requireNotNull(findResponsibleMapper(qualifier)) {
            "Responsible mapper for $qualifier not found"
        }

        val outClass = R::class
        return iterable.map { value ->
            responsibleMapper.map(
                value,
                outClass,
                ParametersHolder(qualifier, args)
            )
        }
    }

    @OptIn(InternalMappersApi::class)
    inline fun <reified R : Any> map(value: Any, qualifier: Qualifier, args: Array<Any?>): R {
        if (isLogging) Logger.logInfo("Mappers", "Mapping $qualifier")
        val responsibleMapper = requireNotNull(findResponsibleMapper(qualifier)) {
            "Responsible mapper for $qualifier(in) not found"
        }

        val outClass = R::class
        return responsibleMapper.map(value, outClass, ParametersHolder(qualifier, args))
    }

    @InternalMappersApi
    fun findResponsibleMapper(tag: String): Mapper<out Any>? {
        return mappers[tag]
    }
}

typealias Qualifier = String

