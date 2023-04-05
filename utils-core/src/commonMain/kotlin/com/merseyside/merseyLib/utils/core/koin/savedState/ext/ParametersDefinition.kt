package com.merseyside.merseyLib.utils.core.koin.savedState.ext

import com.merseyside.merseyLib.utils.core.savedState.SavedState
import org.koin.core.parameter.ParametersDefinition

fun ParametersDefinition.addSavedState(savedState: SavedState): ParametersDefinition {
    return { invoke().add(savedState) }
}