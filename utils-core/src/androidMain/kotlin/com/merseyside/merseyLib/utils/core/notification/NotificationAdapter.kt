package com.merseyside.merseyLib.utils.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.merseyside.merseyLib.kotlin.utils.Id
import android.app.Notification as AndroidNotification

actual abstract class NotificationAdapter(private val context: Context) {

    /**
     * Map of interceptors. Key is an interceptor's tag.
     */
    internal lateinit var interceptors: List<NotificationInterceptor>

    abstract fun show(
        context: Context,
        notification: AndroidNotification,
        tag: String,
        notificationId: Id
    ): Boolean

    /**
     * @return true if notification successfully shown.
     */
    actual fun show(notification: Notification): Boolean {
        val interceptor =
            interceptors.find { interceptor -> interceptor.isResponsibleFor(notification) }

        val androidNotification: AndroidNotification = createNotification(notification, interceptor)

        return show(context, androidNotification, notification.tag, notification.notificationId)
    }

    actual fun setInterceptors(list: List<NotificationInterceptor>) {
        interceptors = list
    }

    abstract fun createDefaultDefinition(context: Context): NotificationDefinition

    abstract fun createDefaultChannel(context: Context): NotificationChannel

    private fun createNotification(
        notification: Notification,
        interceptor: NotificationInterceptor?
    ): AndroidNotification {
        interceptor?.intercept(notification)
        return createNotification(notification)
    }

    private fun createNotification(notification: Notification): AndroidNotification{
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = createDefaultChannel(context).also {
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(it)
            }

            channel.id
        } else null

        return NotificationCompat.Builder(context, channelId ?: "")
            .apply(createDefaultDefinition(context))
            .apply(notification.notificationDefinition)
            .build()
    }
}