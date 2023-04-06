package com.merseyside.merseyLib.utils.core.koin.savedState.provider.scope

import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.utils.core.koin.savedState.key.getStateKey
import com.merseyside.merseyLib.utils.core.koin.savedState.provider.StateProvider
import com.merseyside.merseyLib.utils.core.savedState.SavedState
import org.koin.core.scope.Scope

inline fun <reified T> Scope.getSavedState(): SavedState {
    val stateProvider = get<StateProvider>().log("kek1", "state provider")
    val key = getStateKey<T>()

    return stateProvider.provideState(key)
}