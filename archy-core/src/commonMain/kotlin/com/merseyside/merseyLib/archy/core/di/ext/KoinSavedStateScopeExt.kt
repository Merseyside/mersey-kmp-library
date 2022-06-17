package com.merseyside.merseyLib.archy.core.di.ext

import com.merseyside.merseyLib.archy.core.di.state.KoinStateHolder
import com.merseyside.merseyLib.archy.core.di.state.getStateKey
import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.merseyLib.utils.core.state.StateSaver
import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope
import com.merseyside.merseyLib.archy.core.di.state.getSavedStateFromParams
import com.merseyside.merseyLib.utils.core.state.DummySavedState

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