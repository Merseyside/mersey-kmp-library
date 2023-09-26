package com.merseyside.merseyLib.utils.core.notification.di

import com.merseyside.merseyLib.utils.core.notification.Converter
import com.merseyside.merseyLib.utils.core.notification.NotificationInterceptor
import org.koin.core.module.Module
import org.koin.core.module.dsl.new
import org.koin.core.qualifier.Qualifier

inline fun <reified T : Converter<V>, V> Module.notificationConverterOf(
    crossinline constructor: () -> T,
    qualifier: Qualifier? = null
) = declareNotificationConverter(qualifier) { new(constructor) }

inline fun <reified T : Converter<V>, V, reified T1> Module.notificationConverterOf(
    crossinline constructor: (T1) -> T,
    qualifier: Qualifier? = null
) = declareNotificationConverter(qualifier) { new(constructor) }

inline fun <reified T : Converter<V>, V, reified T1, reified T2> Module.notificationConverterOf(
    crossinline constructor: (T1, T2) -> T,
    qualifier: Qualifier? = null
) = declareNotificationConverter(qualifier) { new(constructor) }

inline fun <reified T : NotificationInterceptor> Module.notificationInterceptorOf(
    crossinline constructor: () -> T,
    qualifier: Qualifier? = null
) = declareNotificationInterceptor(qualifier) { new(constructor) }

inline fun <reified T : NotificationInterceptor, reified T1> Module.notificationInterceptorOf(
    crossinline constructor: (T1) -> T,
    qualifier: Qualifier? = null
) = declareNotificationInterceptor(qualifier) { new(constructor) }

inline fun <reified T : NotificationInterceptor, reified T1, reified T2>
        Module.notificationInterceptorOf(
    crossinline constructor: (T1, T2) -> T,
    qualifier: Qualifier? = null
) = declareNotificationInterceptor(qualifier) { new(constructor) }


//inline fun <reified T : Converter<V>, V> ScopeDSL.notificationConverterOf(
//    crossinline constructor: () -> T,
//    qualifier: Qualifier? = null
//) = declareNotificationConverter(qualifier) { new(constructor) }
//
//inline fun <reified T : Converter<V>, V, reified T1> ScopeDSL.notificationConverterOf(
//    crossinline constructor: (T1) -> T,
//    qualifier: Qualifier? = null
//) = declareNotificationConverter(qualifier) { new(constructor) }
//
//inline fun <reified T : Converter<V>, V, reified T1, reified T2> ScopeDSL.notificationConverterOf(
//    crossinline constructor: (T1, T2) -> T,
//    qualifier: Qualifier? = null
//) = declareNotificationConverter(qualifier) { new(constructor) }