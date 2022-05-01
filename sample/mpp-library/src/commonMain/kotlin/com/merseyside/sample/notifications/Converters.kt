package com.merseyside.sample.notifications

import com.merseyside.merseyLib.utils.core.notification.Converter

expect class MessageEntityConverter {
    companion object : Converter<MessageEntity>
}