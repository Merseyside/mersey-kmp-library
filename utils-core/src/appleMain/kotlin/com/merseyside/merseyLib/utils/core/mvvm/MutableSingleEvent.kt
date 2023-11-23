package com.merseyside.merseyLib.utils.core.mvvm

import dev.icerock.moko.mvvm.livedata.LiveData

actual class MutableSingleEvent<T> actual constructor(initialValue: T) : LiveData<T>(initialValue) {

    private var mPending: Boolean = false

    actual override var value: T
        get() = super.value
        set(value) {
            mPending = true
            changeValue(value)
        }

    actual fun postValue(value: T) {
        this.value = value
    }
}