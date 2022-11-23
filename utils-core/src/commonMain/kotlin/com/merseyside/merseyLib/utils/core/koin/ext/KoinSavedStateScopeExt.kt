package com.merseyside.merseyLib.utils.core.koin.ext

import com.merseyside.merseyLib.utils.core.koin.state.KoinStateHolder
import com.merseyside.merseyLib.utils.core.koin.state.getSavedStateFromParams
import com.merseyside.merseyLib.utils.core.koin.state.getStateKey
import com.merseyside.merseyLib.utils.core.state.DummySavedState
import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.merseyLib.utils.core.state.StateSaver
import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope

inline fun <reified T> getSavedStateFromParams(paramsHolder: ParametersHolder): SavedState {
    return getSavedStateFromParams(paramsHolder, getStateKey<T>()) ?: DummySavedState()
}

inline fun <reified T> Scope.getSavedStateFromStateHolder(): SavedState {
    val stateHolder = getKoinStateHolder() ?: throw NullPointerException()
    return stateHolder.getSavedState<T>()
}

fun Scope.addStateSaver(stateSaver: StateSaver) {
    getKoinStateHolder()?.addStateSaver(stateSaver)
}

inline fun Scope.getKoinStateHolder(): KoinStateHolder? = getOrNull()