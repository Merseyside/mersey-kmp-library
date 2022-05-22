package com.merseyside.merseyLib.utils.core.notification

import kotlin.reflect.KClass

class NotificationBuilder(
    private var notificationAdapter: NotificationAdapter,
    private var converters: List<Converter<*>>,
) {

    var notificationInterceptor: NotificationInterceptor? = null
        private set

    constructor(
        notificationAdapter: NotificationAdapter,
        vararg converters: Converter<*>
    ) : this(notificationAdapter, converters.toList())

    fun setInterceptor(interceptor: NotificationInterceptor) {
        this.notificationInterceptor = interceptor
    }

    fun addConverter(converter: Converter<*>) {
        converters = converters.toMutableList().apply {
            add(converter)
        }
    }

    inline fun <reified T : Any> create(data: T): Notification {
        val plugin = findResponsiblePlugin(T::class)
        return createWithPlugin(plugin, data)
    }

    fun <T> createWithPlugin(converter: Converter<T>, data: T): Notification {
        return converter.createNotification(data).also { notification ->
            notification.setAdapter(notificationAdapter)
            notificationInterceptor?.let { notification.setInterceptor(it) }
        }
    }

    @Throws(IllegalArgumentException::class)
    fun <T : Any> findResponsiblePlugin(clazz: KClass<T>): Converter<T> {
        return converters.find { it.isResponsibleFor(clazz) } as? Converter<T>
            ?: throw IllegalArgumentException(
                "Plugin for ${clazz} not found! Are you sure you add it?"
            )
    }
}