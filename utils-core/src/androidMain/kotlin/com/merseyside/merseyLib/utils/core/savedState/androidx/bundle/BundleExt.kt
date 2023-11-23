package com.merseyside.merseyLib.utils.core.savedState.androidx.bundle

import android.os.Bundle
import com.merseyside.merseyLib.utils.core.savedState.SavedState
import com.merseyside.utils.ext.put
import com.merseyside.utils.ext.putSafe

fun Bundle.toSavedState(): SavedState {
    val savedState = SavedState()
    keySet().forEach { key ->
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
    preSave()

    return Bundle().also { bundle ->
        container.forEach { (key, value) ->
            if (value is SavedState) bundle.put(key, value.toBundle())
            else bundle.putSafe(key, value)
        }
    }
}