package com.merseyside.merseyLib.utils.core.notification

expect abstract class NotificationInterceptor {

    abstract val tag: String

    open fun isResponsibleFor(notification: Notification): Boolean

    fun intercept(notification: Notification)
}