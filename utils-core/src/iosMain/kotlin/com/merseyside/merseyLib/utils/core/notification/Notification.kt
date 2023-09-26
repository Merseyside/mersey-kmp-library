package com.merseyside.merseyLib.utils.core.notification

import com.merseyside.merseyLib.kotlin.utils.Id
import kotlin.reflect.KClass

/**
 * Tag adds to notifications. Can be used to identify notification.
 */
actual class Notification private actual constructor(tag: String, notificationId: Id) {

    /**
     * @return true if notification successfully showed.
     */
    actual fun show(): Boolean {
        TODO()
    }

    internal actual fun setAdapter(notificationAdapter: NotificationAdapter) {
        TODO()
    }
}

actual abstract class Converter<T> {

    actual fun createNotification(data: T,notificationId: Id): Notification {
        TODO()
    }

    internal actual fun isResponsibleFor(clazz: KClass<*>): Boolean {
        TODO()
    }
}