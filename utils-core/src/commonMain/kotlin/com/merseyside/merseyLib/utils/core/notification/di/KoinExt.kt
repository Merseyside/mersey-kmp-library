package com.merseyside.merseyLib.utils.core.notification.di

import com.merseyside.merseyLib.utils.core.koin.multibinding.declareList
import com.merseyside.merseyLib.utils.core.koin.multibinding.getList
import com.merseyside.merseyLib.utils.core.koin.multibinding.intoList
import com.merseyside.merseyLib.utils.core.notification.Converter
import com.merseyside.merseyLib.utils.core.notification.NotificationAdapter
import com.merseyside.merseyLib.utils.core.notification.NotificationBuilder
import com.merseyside.merseyLib.utils.core.notification.NotificationInterceptor
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.bind


/**
 * Adds converter to multibinding.
 */
inline fun <reified T : Converter<V>, V> Module.declareNotificationConverter(
    converter: T,
    qualifier: Qualifier? = null,
) {
    declareNotificationConverter(qualifier) { converter }
}

inline fun <reified T : Converter<V>, V> Module.declareNotificationConverter(
    qualifier: Qualifier? = null,
    crossinline definition: Scope.() -> T
) {
    if (qualifier != null) intoList<Converter<*>, T>(qualifier, definition)
    else intoList<Converter<*>, T>(definition = definition)
}

/**
 * Adds interceptor to multibinding.
 */
inline fun <reified T : NotificationInterceptor> Module.declareNotificationInterceptor(
    qualifier: Qualifier? = null,
    crossinline definition: Scope.() -> T
) {
    if (qualifier != null) intoList<NotificationInterceptor, T>(qualifier, definition)
    else intoList<NotificationInterceptor, T>(definition = definition)
}

inline fun Module.singleNotificationAdapter(
    qualifier: Qualifier? = null,
    noinline definition: Scope.() -> NotificationAdapter
): KoinDefinition<out NotificationAdapter> {

    declareList<NotificationInterceptor>(qualifier)
    return single(qualifier) {
        val adapter: NotificationAdapter = definition()
        adapter.apply { setInterceptors(getList(qualifier)) }
    } bind NotificationAdapter::class
}

/**
 * Creates single notification builder with converters provided through multibinding feature.
 */
fun Module.singleNotificationBuilder(
    qualifier: Qualifier? = null
): KoinDefinition<NotificationBuilder> {
    declareList<Converter<*>>(qualifier)
    return single(qualifier) {
        NotificationBuilder(get(), getList(qualifier))
    }
}