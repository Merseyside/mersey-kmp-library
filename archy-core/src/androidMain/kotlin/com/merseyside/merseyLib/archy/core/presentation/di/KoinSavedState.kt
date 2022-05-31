package com.merseyside.merseyLib.archy.core.presentation.di

import android.os.Bundle
import com.merseyside.merseyLib.archy.core.presentation.ext.toSavedState
import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel.Companion.INSTANCE_STATE_KEY
import com.merseyside.merseyLib.kotlin.extensions.log
import com.merseyside.merseyLib.utils.core.state.SavedState
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.koin.core.parameter.ParametersHolder
import com.merseyside.utils.ext.getSerialize
import com.merseyside.utils.ext.isNotNullAndEmpty
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

actual fun getSavedState(parametersHolder: ParametersHolder): SavedState {
    return parametersHolder.getOrNull<Bundle>().toSavedState()
}

actual fun Scope.getSavedState(
    qualifier: Qualifier
): SavedState {
    return getOrNull<Bundle>(qualifier).toSavedState()
}

