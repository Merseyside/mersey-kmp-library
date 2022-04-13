package com.merseyside.merseyLib.utils.core.mvvm

import dev.icerock.moko.mvvm.livedata.LiveData

actual class MutableSingleEvent<T> : LiveData<T> {

    actual constructor(initialValue: T)
            : super(ArchSingleLiveEvent<T>().apply { value = initialValue })

    @Suppress("UNCHECKED_CAST")
    actual override var value: T
        get() {
            return archLiveData.value as T
        }
        set(value) {
            (archLiveData as ArchSingleLiveEvent<T>).value = value
        }

    actual fun postValue(value: T) {
        (archLiveData as ArchSingleLiveEvent<T>).postValue(value)
    }
}