package com.merseyside.merseyLib.utils.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.kotlin.utils.Id

actual abstract class NotificationAdapter(
    private val context: Context
) {

    abstract fun show(
        context: Context,
        tag: String,
        builder: NotificationCompat.Builder,
        notificationId: Id
    ): Boolean

    /**
     * @return true if notification successfully shown.
     */

    abstract fun getNotificationChannel(needToHide: Boolean): String

    actual fun show(notification: Notification, needToHide: Boolean): Boolean {
        createNotificationChannel(
            if (needToHide)  createDefaultPriorityPriorityNotificationChannel()
            else createHighPriorityNotificationChannel()
        )

        with(notification) {
            return show(
                context,
                tag,
                createNotificationBuilder(
                    notificationDefinition,
                    getNotificationChannel(needToHide)
                ),
                notification.notificationId
            )
        }
    }

    open fun defaultDefinition(context: Context): NotificationDefinition = {}

    private fun createNotificationBuilder(
        builder: NotificationDefinition,
        notificationChannel: String,
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, notificationChannel.log("kekekek"))
            .apply(defaultDefinition(context))
            .apply(builder)
    }

    private fun createNotificationChannel(channel: NotificationChannel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    abstract fun createHighPriorityNotificationChannel(): NotificationChannel
    abstract fun createDefaultPriorityPriorityNotificationChannel(): NotificationChannel
}