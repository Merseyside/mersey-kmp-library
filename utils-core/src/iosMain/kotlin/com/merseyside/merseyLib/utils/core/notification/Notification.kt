package com.merseyside.merseyLib.utils.core.notification

import com.merseyside.merseyLib.kotlin.utils.Id
import kotlin.reflect.KClass

actual class Notification private actual constructor(
    /**
     * Tag adds to notifications. Can be used to identify notification.
     */
    val tag: String,
    val  notificationId: Id
) {

    /**
     * @return true if notification successfully showed.
     */
    actual fun show(needToHide:Boolean): Boolean {
        return false
    }

    internal actual fun setInterceptor(notificationInterceptor: NotificationInterceptor) {

    }

    internal actual fun setAdapter(notificationAdapter: NotificationAdapter) {

    }
}


actual abstract class Converter<T> {

    actual fun createNotification(data: T,notificationId: Id): Notification {
        TODO()
    }

    internal actual fun isResponsibleFor(clazz: KClass<*>): Boolean {
        return false
    }
}

actual abstract class NotificationInterceptor {

    actual abstract fun intercept(notification: Notification)
}