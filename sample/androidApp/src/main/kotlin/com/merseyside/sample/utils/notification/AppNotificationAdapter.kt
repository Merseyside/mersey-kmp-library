package com.merseyside.sample.utils.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.merseyside.merseyLib.utils.core.notification.NotificationAdapter
import com.merseyside.merseyLib.utils.core.notification.NotificationDefinition
import com.merseyside.sample.R
import com.merseyside.sample.view.MainActivity

class AppNotificationAdapter(
    context: Context
): NotificationAdapter(context, CHANNEL_ID) {

    override fun defaultDefinition(context: Context): NotificationDefinition = {
        priority = NotificationCompat.PRIORITY_DEFAULT
        setVibrate(longArrayOf(200, 50, 200))
        setContentIntent(getPendingIntent(context))
        setSmallIcon(R.drawable.ic_notification)
        setChannelId(CHANNEL_ID)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(): NotificationChannel {
        val name: CharSequence = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description: String = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance: Int = NotificationManager.IMPORTANCE_HIGH

        return NotificationChannel(CHANNEL_ID, name, importance).apply {
            this.description = description
            enableLights(true)
            lightColor = Color.GREEN
        }
    }

    override fun show(
        context: Context,
        tag: String,
        builder: NotificationCompat.Builder
    ): Boolean {
        val notification: Notification = builder.build()
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

        // Show the notification
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        } else NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)

        return true
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        return PendingIntent.getActivity(
            context, 0,
            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        private const val VERBOSE_NOTIFICATION_CHANNEL_NAME = "Verbose notification channel name"
        private const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Verbose notification channel description"
        private const val CHANNEL_ID = "342"
        private val NOTIFICATION_ID = 112
    }

}