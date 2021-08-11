package com.merseyside.merseyLib.utils.core.time.ext

import com.merseyside.merseyLib.utils.core.time.Years

fun Years.isLeap(): Boolean {
    return value % 4 == 0
}