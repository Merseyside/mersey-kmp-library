package com.merseyside.merseyLib.utils.core.koin.savedState.provider

import androidx.savedstate.SavedStateRegistry
import com.merseyside.merseyLib.utils.core.koin.savedState.provider.androidx.getSavedState
import com.merseyside.merseyLib.utils.core.koin.savedState.provider.androidx.registerSavedState
import com.merseyside.merseyLib.utils.core.savedState.SavedState

actual class StateProvider(private val savedStateRegistry: SavedStateRegistry) {

    actual fun provideState(key: String): SavedState {
        val savedState = getSavedState(savedStateRegistry, key)
        registerSavedState(savedStateRegistry, savedState, key)

        return savedState
    }


}