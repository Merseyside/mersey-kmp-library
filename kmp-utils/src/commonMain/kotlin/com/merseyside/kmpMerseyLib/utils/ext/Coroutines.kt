package com.merseyside.kmpMerseyLib.utils.ext

import com.merseyside.kmpMerseyLib.utils.time.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

fun <T> CoroutineScope.asyncIO(ioFun: () -> T) = async(Dispatchers.Default) { ioFun() }

suspend fun CoroutineScope.delay(timeUnit: TimeUnit) = kotlinx.coroutines.delay(timeUnit.toMillisLong())