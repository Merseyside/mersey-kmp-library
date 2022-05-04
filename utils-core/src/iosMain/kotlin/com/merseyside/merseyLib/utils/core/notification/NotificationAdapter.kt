package com.merseyside.merseyLib.utils.core.notification

actual abstract class NotificationAdapter {
    /**
     * @return true if notification successfully shown.
     */
    actual fun show(notification: Notification): Boolean {
        TODO("Not yet implemented")
    }
}