package com.merseyside.merseyLib.utils.core.mappers

import com.merseyside.merseyLib.utils.core.mappers.annotations.InternalMappersApi
import kotlin.reflect.typeOf

class ParametersHolder(
    val qualifier: Qualifier,
    params: Array<out Any?>
) {

    @InternalMappersApi
    var currentIndex = 0

    @Suppress("PropertyName")
    val _values = Array<Any?>(5) { null }

    init {
        params.forEachIndexed { index, param ->
            _values[index] = param
        }
    }

    @OptIn(InternalMappersApi::class)
    inline fun <reified T> get(): T {
        return getCurrentIndexValue()
    }

    inline fun <reified T> getOrNull(): T? {
        return try {
            get()
        } catch (e: Exception) {
            null
        }
    }

    @InternalMappersApi
    @Throws(IndexOutOfBoundsException::class, NullPointerException::class)
    inline fun <reified T> getCurrentIndexValue(): T {
        return elementAt<T>(currentIndex).also { currentIndex++ }
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(IndexOutOfBoundsException::class, NullPointerException::class)
    inline fun <reified T> elementAt(index: Int): T {
        if (_values.size <= index) {
            val ktype = typeOf<T>()
            if (!ktype.isMarkedNullable) {
                throw IndexOutOfBoundsException(
                    "Param $ktype with index $index" +
                            " not passed while mapping $qualifier"
                )
            }
        }

        return try {
            _values[index] as T
        } catch (e: NullPointerException) {
            val ktype = typeOf<T>()
            throw NullPointerException("Non-null param $ktype expected but has null or hasn't" +
                    " been passed at all. Error while mapping $qualifier")
        } catch (e: ClassCastException) { // just highlights ClassCastException
            throw e
        }
    }

    inline operator fun <reified T> component1(): T = elementAt(0)
    inline operator fun <reified T> component2(): T = elementAt(1)
    inline operator fun <reified T> component3(): T = elementAt(2)
    inline operator fun <reified T> component4(): T = elementAt(3)
    inline operator fun <reified T> component5(): T = elementAt(4)
}