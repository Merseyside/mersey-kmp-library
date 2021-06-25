@file:JvmName("AndroidByteArrayExt")
package com.merseyside.merseyLib.utils.core.ext

import android.util.Base64

actual fun ByteArray.encodeBase64(): ByteArray {
    return Base64.encode(this, Base64.DEFAULT)
}

actual fun ByteArray.decodeBase64(): ByteArray {
    return Base64.decode(this, Base64.DEFAULT)
}

