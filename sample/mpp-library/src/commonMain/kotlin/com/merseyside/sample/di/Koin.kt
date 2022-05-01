package com.merseyside.sample.di

import com.merseyside.merseyLib.utils.core.notification.di.declareNotificationConverter
import com.merseyside.merseyLib.utils.core.notification.di.singleNotificationBuilder
import com.merseyside.sample.notifications.MessageEntityConverter
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import com.merseyside.sample.notifications.NotificationTest

fun initKoin(
    vararg appModules: Module,
    appDeclaration: KoinAppDeclaration = {}
): KoinApplication {
    val koinApplication = startKoin {
        appDeclaration()
        modules(
            *appModules,
            appModule
        )

    }

    return koinApplication
}

private val appModule = module {

    singleNotificationBuilder()
    declareNotificationConverter(MessageEntityConverter)

    singleOf(::NotificationTest)
}