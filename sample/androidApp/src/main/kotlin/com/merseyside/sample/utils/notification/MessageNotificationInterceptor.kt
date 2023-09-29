package com.merseyside.sample.utils.notification

import android.app.NotificationChannel
import android.content.Context
import com.merseyside.merseyLib.utils.core.notification.Notification
import com.merseyside.merseyLib.utils.core.notification.NotificationInterceptor

class MessageNotificationInterceptor(context: Context) : NotificationInterceptor(context) {

    override val tag: String = "message"

    override fun isResponsibleFor(notification: Notification): Boolean {
        return super.isResponsibleFor(notification) && notification.notificationId == 0L
    }

    override fun getChannelId(notification: Notification): String? {
        return "1"
    }

    override fun createChannel(id: String): NotificationChannel {
        TODO("Not yet implemented")
    }
}