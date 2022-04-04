package com.merseyside.merseyLib.archy.core.presentation.di

import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel
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
            getSavedState(it)?.let { savedState ->
                onRestoreState(savedState)
            }
        }
    }
}