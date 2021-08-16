@file:JvmName("AndroidStringExt")
package com.merseyside.merseyLib.utils.core.ext

import android.util.Base64

actual fun String.encodeBase64(): String {
    return Base64.encodeToString(toByteArray(), Base64.DEFAULT)
}

actual fun String.decodeBase64(): String {
    return Base64.decode(this, Base64.DEFAULT).toString()
}