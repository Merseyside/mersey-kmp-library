package com.merseyside.merseyLib.utils.core.mvvm

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.atomic.AtomicBoolean

class ArchSingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending: AtomicBoolean = AtomicBoolean(false)

    override fun getValue(): T? {
        return if (mPending.get()) {
            mPending.set(false)
            try {
                super.getValue()
            } finally {
                value = null
            }
        } else null
    }

    @MainThread
    override fun setValue(t: T?) {
        if (t != null) {
            mPending.set(true)
        }
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}