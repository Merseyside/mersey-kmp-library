package com.merseyside.merseyLib.utils.core.savedState.validator

import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import java.io.Serializable

actual fun validateSavedStateValue(value: Any?): Boolean {
    if (value == null) {
        return true
    }
    for (cl in ACCEPTABLE_CLASSES) {
        if (cl!!.isInstance(value)) {
            return true
        }
    }
    return false
}

private val ACCEPTABLE_CLASSES = arrayOf( // baseBundle
    Boolean::class.javaPrimitiveType,
    BooleanArray::class.java,
    Double::class.javaPrimitiveType,
    DoubleArray::class.java,
    Int::class.javaPrimitiveType,
    IntArray::class.java,
    Long::class.javaPrimitiveType,
    LongArray::class.java,
    String::class.java,
    Array<String>::class.java, // bundle
    Binder::class.java,
    Bundle::class.java,
    Byte::class.javaPrimitiveType,
    ByteArray::class.java,
    Char::class.javaPrimitiveType,
    CharArray::class.java,
    CharSequence::class.java,
    Array<CharSequence>::class.java,
    // type erasure ¯\_(ツ)_/¯, we won't eagerly check elements contents
    ArrayList::class.java,
    Float::class.javaPrimitiveType,
    FloatArray::class.java,
    Parcelable::class.java,
    Array<Parcelable>::class.java,
    Serializable::class.java,
    Short::class.javaPrimitiveType,
    ShortArray::class.java,
    SparseArray::class.java,
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        Size::class.java
    else
        Int::class.javaPrimitiveType,
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        SizeF::class.java
    else
        Int::class.javaPrimitiveType
)