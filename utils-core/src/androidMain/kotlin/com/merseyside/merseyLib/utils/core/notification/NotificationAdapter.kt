package com.merseyside.merseyLib.utils.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

actual abstract class NotificationAdapter(
    private val context: Context,
    private val channelId: String
) {

    abstract fun show(
        context: Context,
        tag: String,
        builder: NotificationCompat.Builder
    ): Boolean

    /**
     * @return true if notification successfully shown.
     */
    actual fun show(notification: Notification): Boolean {
        with(notification) {
            setNotificationChannel(createNotificationChannel())
            return show(context, tag, createNotificationBuilder(notificationDefinition))
        }
    }

    open fun defaultDefinition(context: Context): NotificationDefinition = {}

    private fun createNotificationBuilder(
        builder: NotificationDefinition
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .apply(defaultDefinition(context))
            .apply(builder)
    }

    private fun setNotificationChannel(channel: NotificationChannel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    abstract fun createNotificationChannel(): NotificationChannel
}