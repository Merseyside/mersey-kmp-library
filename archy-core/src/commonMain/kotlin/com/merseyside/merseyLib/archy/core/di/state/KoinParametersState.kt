package com.merseyside.merseyLib.archy.core.di.state

import com.merseyside.merseyLib.utils.core.state.SavedState
import org.koin.core.parameter.ParametersHolder

expect fun getSavedStateFromParams(
    parametersHolder: ParametersHolder,
    key: String
): SavedState?