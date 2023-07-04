package com.merseyside.merseyLib.utils.core.koin.savedState.scope

import com.merseyside.merseyLib.utils.core.savedState.SavedState
import org.koin.core.parameter.ParametersHolder

fun ParametersHolder.addSavedState(savedState: SavedState) {
    add(savedState)
}