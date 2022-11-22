package com.merseyside.merseyLib.utils.core.koin.state

import android.os.Bundle
import com.merseyside.merseyLib.utils.core.state.SavedState
import org.koin.core.parameter.ParametersHolder

actual fun getSavedStateFromParams(parametersHolder: ParametersHolder, key: String): SavedState? {
    return parametersHolder.getOrNull<Bundle>()
        ?.getBundle(key)
        ?.toSavedState()
}