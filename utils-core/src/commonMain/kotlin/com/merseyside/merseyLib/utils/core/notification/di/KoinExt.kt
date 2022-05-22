package com.merseyside.merseyLib.utils.core.notification.di

import com.merseyside.merseyLib.utils.core.koin.declareMultibinding
import com.merseyside.merseyLib.utils.core.koin.getMultibindingList
import com.merseyside.merseyLib.utils.core.koin.intoMultibinding
import com.merseyside.merseyLib.utils.core.notification.NotificationInterceptor
import com.merseyside.merseyLib.utils.core.notification.NotificationBuilder
import com.merseyside.merseyLib.utils.core.notification.Converter
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.ext.getFullName

val notificationPluginQualifier = named("notification_plugin_qualifier")

/**
 * Adds converter to multibinding.
 */
fun Module.declareNotificationConverter(converter: Converter<*>) {
    intoMultibinding(
        notificationPluginQualifier,
        key = converter::class.getFullName(),
        value = converter
    )
}

/**
 * Creates single notification builder with converters provided through multibinding feature.
 */
fun Module.singleNotificationBuilder() {
    declareMultibinding<String, Converter<*>>(notificationPluginQualifier)
    single {
        NotificationBuilder(
            get(),
            getMultibindingList(notificationPluginQualifier)
        ).apply {
            getOrNull<NotificationInterceptor>()?.let { setInterceptor(it) }
        }
    }
}