package com.merseyside.merseyLib.utils.core.mvvm

import dev.icerock.moko.mvvm.livedata.LiveData

actual class MutableSingleEvent actual constructor(initialValue: T)<T> : LiveData<T> {

    actual open var value: T
        get() = TODO("Not yet implemented")
        set(value) {}

    actual fun postValue(value: T) {
    }

}