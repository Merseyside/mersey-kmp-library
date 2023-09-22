package com.merseyside.merseyLib.utils.core.notification

import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

actual class Notification private actual constructor(
    val tag: String
) {

    internal lateinit var notificationDefinition: NotificationDefinition

    private var notificationAdapter: NotificationAdapter? = null
    private var notificationInterceptor: NotificationInterceptor? = null

    constructor(
        tag: String,
        definition: NotificationDefinition
    ): this(tag) {
        notificationDefinition = definition
    }

    /**
     * @return true if notification successfully showed.
     */
    @Throws(IllegalStateException::class)
    actual fun show(): Boolean {
        return notificationAdapter?.run {
            notificationInterceptor?.intercept(this@Notification)
            show(this@Notification)
        } ?: throw IllegalStateException("Notification adapter doesn't set")
    }

    internal actual fun setInterceptor(notificationInterceptor: NotificationInterceptor) {
        this.notificationInterceptor = notificationInterceptor
    }

    internal actual fun setAdapter(notificationAdapter: NotificationAdapter) {
        this.notificationAdapter = notificationAdapter
    }
}

actual abstract class Converter<T> {

    abstract val tag: String

    actual fun createNotification(data: T): Notification {
        return Notification(tag, convert(data))
    }

    protected abstract fun convert(data: T): NotificationDefinition

    internal actual fun isResponsibleFor(clazz: KClass<*>): Boolean {
        return parameterizedType == clazz.java
    }

    private val parameterizedType = (this.javaClass
        .genericSuperclass as ParameterizedType)
        .actualTypeArguments[0] as Class<T>
}

actual abstract class NotificationInterceptor {

    actual abstract fun intercept(notification: Notification)
}