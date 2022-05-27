package com.merseyside.merseyLib.archy.core.presentation.di

import com.merseyside.merseyLib.archy.core.presentation.viewModel.StateViewModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

inline fun <reified T : ViewModel> Module.viewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> {
    return factory(qualifier, definition)
}

inline fun <reified T : StateViewModel> Module.stateViewModel(
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