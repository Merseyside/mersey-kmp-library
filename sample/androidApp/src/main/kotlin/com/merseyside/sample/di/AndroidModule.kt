package com.merseyside.sample.di

import com.merseyside.merseyLib.utils.core.notification.Notification
import com.merseyside.merseyLib.utils.core.notification.NotificationAdapter
import com.merseyside.merseyLib.utils.core.notification.NotificationInterceptor
import com.merseyside.sample.utils.notification.AppNotificationAdapter
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val androidModule = module {
    single<NotificationInterceptor> {
        object : NotificationInterceptor() {
            override fun intercept(notification: Notification) {

            }
        }
    }

    singleOf(::AppNotificationAdapter) bind NotificationAdapter::class
}