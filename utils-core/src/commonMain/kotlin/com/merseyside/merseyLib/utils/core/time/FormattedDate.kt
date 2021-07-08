package com.merseyside.merseyLib.utils.core.time

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class FormattedDate(val formattedDate: String)