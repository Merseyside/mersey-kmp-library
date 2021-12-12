package com.merseyside.merseyLib.utils.core.mvvm

import dev.icerock.moko.mvvm.livedata.LiveData

actual class MutableSingleEvent<T> actual constructor(initialValue: T) : LiveData<T>(initialValue) {

    actual override var value: T
        get() = TODO("Not yet implemented")
        set(value) {}

    actual fun postValue(value: T) {
    }

}