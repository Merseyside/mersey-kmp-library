package com.merseyside.merseyLib.utils.core.mvvm

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class ArchSingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending: AtomicBoolean = AtomicBoolean(false)

    override fun getValue(): T? {
        return if (mPending.get()) {
            try {
                super.getValue()
            } finally {
                clear()
            }
        } else null
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val obs = Observer<T> {
            if (mPending.get()) {
                observer.onChanged(it)
            }
        }
        clear()
        super.observe(owner, obs)
    }

    private fun clear() {
        mPending.set(false)
        super.setValue(null)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}