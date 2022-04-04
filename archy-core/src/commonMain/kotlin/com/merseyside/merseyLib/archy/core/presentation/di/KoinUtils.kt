package com.merseyside.merseyLib.archy.core.presentation.di

import com.merseyside.merseyLib.utils.core.SavedState
import org.koin.core.parameter.ParametersHolder

expect fun getSavedState(parametersHolder: ParametersHolder): SavedState?