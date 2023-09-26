package com.merseyside.sample.di

import com.merseyside.merseyLib.utils.core.notification.NotificationAdapter
import com.merseyside.merseyLib.utils.core.notification.di.declareNotificationInterceptor
import com.merseyside.merseyLib.utils.core.notification.di.singleNotificationAdapter
import com.merseyside.sample.utils.notification.AppNotificationAdapter
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.core.qualifier.named
import com.merseyside.sample.utils.notification.MessageNotificationInterceptor

val androidModule = module {

    declareNotificationInterceptor {
        MessageNotificationInterceptor(get())
    }

    singleNotificationAdapter {
        AppNotificationAdapter(get())
    }
}