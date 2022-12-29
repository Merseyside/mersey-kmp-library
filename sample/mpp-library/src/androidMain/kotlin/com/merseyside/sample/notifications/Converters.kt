package com.merseyside.sample.notifications

import com.merseyside.merseyLib.utils.core.notification.Converter
import com.merseyside.merseyLib.utils.core.notification.NotificationDefinition

actual class MessageEntityConverter {

    actual companion object : Converter<MessageEntity>() {
        override val tag: String = "message"

        override fun convert(
            data: MessageEntity
        ): NotificationDefinition {
            return {
                with(data) {
                    //setContentTitle(sender)
                    //setContentText(message)
                }
            }
        }

    }
}