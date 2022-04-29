package com.merseyside.merseyLib.utils.core.notification

import kotlin.reflect.KClass

expect class Notification private constructor(
    /**
     * Tag adds to notifications. Can be used to identify notification.
     */
    tag: String
) {

    /**
     * @return true if notification successfully showed.
     */
    fun show(): Boolean

    internal fun setInterceptor(notificationInterceptor: NotificationInterceptor)

    internal fun setAdapter(notificationAdapter: NotificationAdapter)
}

expect abstract class Converter<T>() {

    fun createNotification(data: T): Notification

    internal fun isResponsibleFor(clazz: KClass<*>): Boolean
}

expect abstract class NotificationInterceptor() {

    abstract fun intercept(notification: Notification)
}