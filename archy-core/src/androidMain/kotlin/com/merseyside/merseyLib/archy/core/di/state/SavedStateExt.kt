@file:JvmName("StateExtAndroid")
package com.merseyside.merseyLib.archy.core.di.state

import android.os.Bundle
import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.merseyLib.utils.core.state.StateSaver
import com.merseyside.merseyLib.utils.core.state.forEach
import com.merseyside.utils.ext.put

fun SavedState.toBundle(): Bundle {
    return Bundle().apply {
        forEach { key, value ->
            if (value is SavedState) put(key, value.toBundle())
            else this.put(key, value)
        }
    }
}

inline fun <reified T : StateSaver> T.saveState(outState: Bundle, key: String) {
    val state = SavedState()
    onSaveState(state)
    outState.putBundle(key, state.toBundle())
}