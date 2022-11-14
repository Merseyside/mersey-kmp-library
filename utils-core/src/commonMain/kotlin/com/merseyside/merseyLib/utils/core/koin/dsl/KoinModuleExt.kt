package com.merseyside.merseyLib.utils.core.koin.dsl

import com.merseyside.merseyLib.utils.core.koin.ext.getSavedStateFromParams
import com.merseyside.merseyLib.utils.core.koin.state.StateDefinition
import com.merseyside.merseyLib.utils.core.state.StateSaver
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

inline fun <reified T> Module.stateViewModel(
    qualifier: Qualifier? = null,
    noinline viewModelDefinition: StateDefinition<T>
): Pair<Module, InstanceFactory<T>> where T : ViewModel, T : StateSaver {
    return factory(qualifier) { paramsHolder ->
        viewModelDefinition(
            getSavedStateFromParams<T>(paramsHolder),
            paramsHolder
        )
    }
}