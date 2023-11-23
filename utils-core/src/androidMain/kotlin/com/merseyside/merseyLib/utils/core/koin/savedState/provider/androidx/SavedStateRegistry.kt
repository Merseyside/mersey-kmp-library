package com.merseyside.merseyLib.utils.core.koin.savedState.provider.androidx

import androidx.savedstate.SavedStateRegistry
import com.merseyside.merseyLib.utils.core.savedState.androidx.bundle.toBundle
import com.merseyside.merseyLib.utils.core.savedState.androidx.bundle.toSavedState
import com.merseyside.merseyLib.utils.core.savedState.SavedState

fun getSavedState(savedStateRegistry: SavedStateRegistry, key: String): SavedState {
    val bundle = savedStateRegistry.consumeRestoredStateForKey(key)
    return bundle?.toSavedState() ?: SavedState()
}

fun registerSavedState(savedStateRegistry: SavedStateRegistry, savedState: SavedState, key: String) {
    savedStateRegistry.registerSavedStateProvider(key) {
        savedState.toBundle()
    }
}