package com.merseyside.merseyLib.archy.core.presentation.di

import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.ScopeDSL

inline fun <reified T : ViewModel> Module.viewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> {
    return factory(qualifier, definition)
}

@Deprecated("Use stateViewModel",
    ReplaceWith("ScopeDSL.stateViewModel")
)
inline fun <reified T : StateViewModel> Module.oldStateViewModel(
    qualifier: Qualifier? = null,
    noinline viewModelDefinition: Definition<T>
): Pair<Module, InstanceFactory<T>> {

    return factory(qualifier) {
        viewModelDefinition(it)
    }
}

inline fun <reified T : StateViewModel> Module.stateViewModel(
    qualifier: Qualifier? = null,
    noinline viewModelDefinition: StateDefinition<T>
): Pair<Module, InstanceFactory<T>> {
    return factory(qualifier) {
        viewModelDefinition(getSavedState(it), it)
    }
}