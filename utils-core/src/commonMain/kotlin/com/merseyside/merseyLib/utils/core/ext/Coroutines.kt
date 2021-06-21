package com.merseyside.merseyLib.utils.core.ext

import com.merseyside.merseyLib.utils.core.time.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

fun <T> CoroutineScope.asyncIO(ioFun: () -> T) = async(Dispatchers.Default) { ioFun() }

suspend fun CoroutineScope.delay(timeUnit: TimeUnit) = kotlinx.coroutines.delay(timeUnit.toMillisLong())