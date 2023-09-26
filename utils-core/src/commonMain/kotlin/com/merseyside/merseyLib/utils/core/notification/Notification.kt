package com.merseyside.merseyLib.utils.core.notification

import com.merseyside.merseyLib.kotlin.utils.Id
import kotlin.reflect.KClass

/**
 * Tag adds to notifications. Can be used to identify notification.
 */
expect class Notification private constructor(tag: String, notificationId: Id) {

    /**
     * @return true if notification successfully showed.
     */
    fun show(): Boolean

    internal fun setAdapter(notificationAdapter: NotificationAdapter)
}

expect abstract class Converter<T>() {

    fun createNotification(data: T,notificationId: Id): Notification

    internal fun isResponsibleFor(clazz: KClass<*>): Boolean
}