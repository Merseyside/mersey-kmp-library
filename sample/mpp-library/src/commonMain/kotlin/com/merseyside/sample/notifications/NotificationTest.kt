package com.merseyside.sample.notifications

import com.merseyside.merseyLib.utils.core.notification.NotificationBuilder

class NotificationTest(
    private val notificationBuilder: NotificationBuilder
) {

    fun showNotification() {
        val message = generateMessage()
        val notification = notificationBuilder.create(message)
        notification.show()
    }

    private fun generateMessage(): MessageEntity {
        return MessageEntity(
            id = 0,
            sender = "Maria",
            message = "My parents are out. Would you like to come?"
        )
    }
}