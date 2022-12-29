package com.merseyside.merseyLib.utils.core.koin.state

import com.merseyside.merseyLib.utils.core.state.SavedState
import org.koin.core.parameter.ParametersHolder

expect fun getSavedStateFromParams(
    parametersHolder: ParametersHolder,
    key: String
): SavedState?