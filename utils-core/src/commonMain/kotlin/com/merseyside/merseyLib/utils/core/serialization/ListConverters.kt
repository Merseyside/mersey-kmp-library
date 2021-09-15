package com.merseyside.merseyLib.utils.core.serialization

interface ListStringConverter {
    fun asList(): List<String?>
}

interface ListIntConverter {
    fun asList(): List<Int?>
}

interface ListFloatConverter {
    fun asList(): List<Float?>
}

interface ListBooleanConverter {
    fun asList(): List<Boolean>
}