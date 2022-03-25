package com.merseyside.merseyLib.utils.core.ext

import com.merseyside.merseyLib.time.units.TimeUnit


suspend fun delay(timeUnit: TimeUnit) = kotlinx.coroutines.delay(timeUnit.millis)