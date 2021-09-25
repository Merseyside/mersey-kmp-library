package com.merseyside.merseyLib.utils.core.mvvm

import dev.icerock.moko.mvvm.livedata.LiveData


expect class MutableSingleEvent<T>(initialValue: T): LiveData<T> {

    override var value: T

    fun postValue(value: T)
}