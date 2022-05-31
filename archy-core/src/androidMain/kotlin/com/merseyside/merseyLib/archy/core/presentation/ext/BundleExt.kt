package com.merseyside.merseyLib.archy.core.presentation.ext

import android.os.Bundle
import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel.Companion.INSTANCE_STATE_KEY
import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.utils.ext.getSerialize
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

fun Bundle?.toSavedState(): SavedState {
    return SavedState().apply {
        if (this@toSavedState != null) {
            addAll(
                this@toSavedState.getSerialize(
                    INSTANCE_STATE_KEY,
                    MapSerializer(String.serializer(), String.serializer())
                ) ?: throw IllegalArgumentException()
            )
        }
    }
}