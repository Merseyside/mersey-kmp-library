package com.merseyside.merseyLib.utils.core.koin.savedState.androidx.bundle

import android.os.Bundle
import com.merseyside.merseyLib.utils.core.savedState.SavedState
import com.merseyside.utils.ext.put

fun Bundle.toSavedState(): SavedState {
    val savedState = SavedState()
    val keys = this@toSavedState.keySet()
    keys.forEach { key ->
        val value = get(key)
        value?.let {
            val newValue = if (value is Bundle) {
                value.toSavedState()
            } else value

            savedState.put(key, newValue)
        }
    }

    return savedState
}

fun SavedState.toBundle(): Bundle {
    return Bundle().apply {
        container.forEach { (key, value) ->
            if (value is SavedState) put(key, value.toBundle())
            else this.put(key, value)
        }
    }
}