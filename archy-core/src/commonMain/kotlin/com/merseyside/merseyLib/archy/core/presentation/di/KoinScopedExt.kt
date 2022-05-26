package com.merseyside.merseyLib.archy.core.presentation.di

import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel
import com.merseyside.merseyLib.utils.core.state.SavedState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.ScopeDSL

inline fun <reified T : ViewModel> ScopeDSL.viewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> {
    return factory(qualifier, definition)
}

@Deprecated("Use newStateViewModel",
    ReplaceWith("ScopeDSL.newStateViewModel")
)
inline fun <reified T : StateViewModel> ScopeDSL.stateViewModel(
    qualifier: Qualifier? = null,
    noinline viewModelDefinition: Definition<T>
): Pair<Module, InstanceFactory<T>> {

    return factory(qualifier) {
        viewModelDefinition(it).apply {
            getSavedState(it)?.let { state ->
                onRestoreState(state)
            }
        }
    }
}

inline fun <reified T> ScopeDSL.state(
    state: T?,
    qualifier: Qualifier? = null
) {
    state?.let { scoped(qualifier) { state } }
}

