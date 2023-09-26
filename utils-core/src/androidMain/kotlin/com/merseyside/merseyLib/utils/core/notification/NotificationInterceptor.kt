package com.merseyside.merseyLib.utils.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat.Builder as Builder
import com.merseyside.merseyLib.kotlin.utils.safeLet

actual abstract class NotificationInterceptor(val context: Context) {

    actual abstract val tag: String

    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    actual open fun isResponsibleFor(notification: Notification): Boolean {
        return notification.tag == tag
    }

    open fun getChannelId(notification: Notification): String? {
        return null
    }

    actual fun intercept(notification: Notification) {
        notification.updateDefinition {
            setupChannel(notification)
        }
    }

    abstract fun createChannel(id: String): NotificationChannel

    private fun Builder.setupChannel(notification: Notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val id = getChannelId(notification)
            val channel = safeLet(id) { id ->
                getChannel(id) ?: createChannel(id).also { newChannel ->
                    notificationManager.createNotificationChannel(newChannel)
                }
            }

            if (channel != null) setChannelId(channel.id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getChannel(id: String): NotificationChannel? {
        return notificationManager.getNotificationChannel(id)
    }
}