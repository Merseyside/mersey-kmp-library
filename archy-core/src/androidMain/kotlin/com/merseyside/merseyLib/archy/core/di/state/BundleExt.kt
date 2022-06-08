package com.merseyside.merseyLib.archy.core.di.state

import android.os.Bundle
import com.merseyside.merseyLib.utils.core.state.SavedState

fun Bundle?.toSavedState(): SavedState {
    val savedState = SavedState()
    if (this != null) {
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
    }

    return savedState
}