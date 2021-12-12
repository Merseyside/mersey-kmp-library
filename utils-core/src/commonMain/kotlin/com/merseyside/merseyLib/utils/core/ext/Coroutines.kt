package com.merseyside.merseyLib.utils.core.ext

import com.merseyside.merseyLib.time.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

suspend fun delay(timeUnit: TimeUnit) = kotlinx.coroutines.delay(timeUnit.millis)