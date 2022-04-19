package com.merseyside.merseyLib.archy.core.presentation.di

import android.os.Bundle
import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel.Companion.INSTANCE_STATE_KEY
import com.merseyside.merseyLib.utils.core.SavedState
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.koin.core.parameter.ParametersHolder
import com.merseyside.utils.ext.getSerialize
import com.merseyside.utils.ext.isNotNullAndEmpty

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