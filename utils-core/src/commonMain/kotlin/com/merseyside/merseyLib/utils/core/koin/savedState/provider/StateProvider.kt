package com.merseyside.merseyLib.utils.core.koin.savedState.provider

import com.merseyside.merseyLib.utils.core.savedState.SavedState

expect class StateProvider {

    fun provideState(key: String): SavedState
}