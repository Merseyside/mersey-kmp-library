package com.merseyside.merseyLib.archy.core.presentation.di

import android.os.Bundle
import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel.Companion.INSTANCE_STATE_KEY
import com.merseyside.merseyLib.utils.core.state.SavedState
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.koin.core.parameter.ParametersHolder
import com.merseyside.utils.ext.getSerialize
import com.merseyside.utils.ext.isNotNullAndEmpty
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

actual fun getSavedState(parametersHolder: ParametersHolder): SavedState? {
    val bundle = parametersHolder.getOrNull<Bundle>()

    return if (bundle.isNotNullAndEmpty()) {
        SavedState().apply {
            addAll(
                bundle.getSerialize(
                    INSTANCE_STATE_KEY,
                    MapSerializer(String.serializer(), String.serializer())
                ) ?: throw IllegalArgumentException()
            )

        }
    } else null

}

actual fun Scope.getSavedState(
    qualifier: Qualifier?,
    parametersHolder: ParametersDefinition?
): SavedState? {
    val bundle = getOrNull<Bundle>(qualifier)
    return if (bundle.isNotNullAndEmpty()) {
        SavedState().apply {
            addAll(
                bundle.getSerialize(
                    INSTANCE_STATE_KEY,
                    MapSerializer(String.serializer(), String.serializer())
                ) ?: throw IllegalArgumentException()
            )

        }
    } else null
}