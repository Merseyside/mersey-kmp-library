package com.merseyside.merseyLib.utils.core.koin.scope.dsl

import com.merseyside.merseyLib.utils.core.koin.ext.getSavedStateFromParams
import com.merseyside.merseyLib.utils.core.koin.state.StateDefinition
import com.merseyside.merseyLib.utils.core.state.StateSaver
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.ScopeDSL

inline fun <reified T : ViewModel> ScopeDSL.viewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return factory(qualifier, definition)
}

inline fun <reified T> ScopeDSL.stateViewModel(
    qualifier: Qualifier? = null,
    noinline viewModelDefinition: StateDefinition<T>
): KoinDefinition<T> where T : ViewModel, T : StateSaver {
    return factory(qualifier) { paramsHolder ->
        viewModelDefinition(
            getSavedStateFromParams<T>(paramsHolder),
            paramsHolder
        )
    }
}
