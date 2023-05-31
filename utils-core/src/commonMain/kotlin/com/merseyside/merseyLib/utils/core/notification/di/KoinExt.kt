package com.merseyside.merseyLib.utils.core.notification.di

import com.merseyside.merseyLib.utils.core.koin.multibinding.declareMultibinding
import com.merseyside.merseyLib.utils.core.koin.multibinding.getMultibindingList
import com.merseyside.merseyLib.utils.core.koin.multibinding.intoMultibinding
import com.merseyside.merseyLib.utils.core.notification.NotificationInterceptor
import com.merseyside.merseyLib.utils.core.notification.NotificationBuilder
import com.merseyside.merseyLib.utils.core.notification.Converter
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.ext.getFullName

val notificationConverterQualifier = named("notification_converter_qualifier")

/**
 * Adds converter to multibinding.
 */
fun Module.declareNotificationConverter(converter: Converter<*>) {
    intoMultibinding(
        notificationConverterQualifier,
        key = converter::class.getFullName(),
        value = converter
    )
}

/**
 * Creates single notification builder with converters provided through multibinding feature.
 */
fun Module.singleNotificationBuilder() {
    declareMultibinding<String, Converter<*>>(notificationConverterQualifier)
    single {
        NotificationBuilder(
            get(),
            getMultibindingList<String, Converter<*>>(notificationConverterQualifier)
        ).apply {
            getOrNull<NotificationInterceptor>()?.let { setInterceptor(it) }
        }
    }
}