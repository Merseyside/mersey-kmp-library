package com.merseyside.merseyLib.utils.core.notification

import com.merseyside.merseyLib.kotlin.utils.Id
import kotlin.reflect.KClass

class NotificationBuilder(
    private var notificationAdapter: NotificationAdapter,
    private var converters: List<Converter<*>>,
) {

    constructor(
        notificationAdapter: NotificationAdapter,
        vararg converters: Converter<*>
    ) : this(notificationAdapter, converters.toList())

    fun addConverter(converter: Converter<*>) {
        converters = converters.toMutableList().apply {
            add(converter)
        }
    }

    inline fun <reified T : Any> create(data: T, notificationId: Id): Notification {
        val converter = findResponsibleConverter(T::class)
        return createWithConverter(converter, data, notificationId)
    }

    fun <T> createWithConverter(converter: Converter<T>, data: T, notificationId: Id): Notification {
        return converter.createNotification(data,notificationId).also { notification ->
            notification.setAdapter(notificationAdapter)
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalArgumentException::class)
    fun <T : Any> findResponsibleConverter(clazz: KClass<T>): Converter<T> {
        return converters.find { it.isResponsibleFor(clazz) } as? Converter<T>
            ?: throw IllegalArgumentException(
                "Converter for $clazz not found! Are you sure you added it?"
            )
    }
}