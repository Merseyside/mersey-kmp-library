package com.merseyside.merseyLib.utils.core.notification

expect abstract class NotificationAdapter {

    /**
     * @return true if notification successfully shown.
     */
    fun show(notification: Notification): Boolean

    fun setInterceptors(list: List<NotificationInterceptor>)
}