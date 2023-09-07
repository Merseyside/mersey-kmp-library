package com.merseyside.merseyLib.utils.core.mappers.annotations

@Retention(value = AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.TYPEALIAS, AnnotationTarget.PROPERTY)
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR, message = "This is an internal Mappers API that " +
            "should not be used from in code"
)
annotation class InternalMappersApi