package com.merseyside.merseyLib.utils.core.mappers

import kotlin.reflect.typeOf

class ParametersHolder(
    val qualifier: Qualifier,
    params: Array<out Any?>
) {

    @Suppress("PropertyName")
    val _values = Array<Any?>(5) { null }

    init {
        params.forEachIndexed { index, param ->
            _values[index] = param
        }
    }

    @Suppress("UNCHECKED_CAST")
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
            throw NullPointerException("Passed param $ktype is null. Error while mapping $qualifier")
        }
    }

    inline operator fun <reified T> component1(): T = elementAt(0)
    inline operator fun <reified T> component2(): T = elementAt(1)
    inline operator fun <reified T> component3(): T = elementAt(2)
    inline operator fun <reified T> component4(): T = elementAt(3)
    inline operator fun <reified T> component5(): T = elementAt(4)
}