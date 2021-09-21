package com.merseyside.merseyLib.archy.android.presentation.di

import android.os.Bundle
import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel
import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel.Companion.INSTANCE_STATE_KEY
import com.merseyside.merseyLib.utils.core.SavedState
import com.merseyside.utils.ext.getSerialize
import com.merseyside.utils.ext.isNotNullAndEmpty
import com.merseyside.utils.ext.log
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.ScopeDSL

inline fun <reified T : StateViewModel> ScopeDSL.stateViewModel(
    qualifier: Qualifier? = null,
    noinline viewModelDefinition: Definition<T>
): Pair<Module, InstanceFactory<T>> {

    return factory(qualifier) {
        viewModelDefinition(it).apply {
            val bundle = it.getOrNull<Bundle>()
            if (bundle.isNotNullAndEmpty()) {
                val savedState = SavedState().apply {
                    addAll(bundle.getSerialize(
                        INSTANCE_STATE_KEY,
                        MapSerializer(String.serializer(), String.serializer())
                    ) ?: throw IllegalArgumentException())
                }

                onRestoreState(savedState)
            }
        }
    }
}