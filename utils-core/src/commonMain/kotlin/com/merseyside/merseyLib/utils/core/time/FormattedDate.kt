package com.merseyside.merseyLib.utils.core.time

import com.merseyside.merseyLib.utils.core.time.ext.toFormattedDate
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class FormattedDate(val value: String) {

    override fun toString(): String {
        return value
    }

    companion object {
        fun empty(): FormattedDate = FormattedDate("")

        fun from(
            timeUnit: TimeUnit,
            pattern: String = TimeConfiguration.defaultPattern
        ): FormattedDate {
            return timeUnit.toFormattedDate(pattern)
        }
    }
}

fun FormattedDate.isEmpty() = value.isEmpty()