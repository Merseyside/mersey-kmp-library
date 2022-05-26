package com.merseyside.merseyLib.archy.core.presentation.di

import com.merseyside.merseyLib.utils.core.state.SavedState
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

expect fun getSavedState(parametersHolder: ParametersHolder): SavedState?

expect fun Scope.getSavedState(
    qualifier: Qualifier? = null,
    parametersHolder: ParametersDefinition? = null
): SavedState?