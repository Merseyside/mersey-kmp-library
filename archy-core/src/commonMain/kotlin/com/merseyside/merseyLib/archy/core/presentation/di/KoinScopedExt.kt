package com.merseyside.merseyLib.archy.core.presentation.di

import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel
import com.merseyside.merseyLib.utils.core.state.SavedState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.ScopeDSL

inline fun <reified T : ViewModel> ScopeDSL.viewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<T>> {
    return factory(qualifier, definition)
}

@Deprecated("Use stateViewModel",
    ReplaceWith("ScopeDSL.stateViewModel")
)
inline fun <reified T : StateViewModel> ScopeDSL.oldStateViewModel(
    qualifier: Qualifier? = null,
    noinline viewModelDefinition: Definition<T>
): Pair<Module, InstanceFactory<T>> {

    return factory(qualifier) {
        viewModelDefinition(it)
    }
}

inline fun <reified T : StateViewModel> ScopeDSL.stateViewModel(
    qualifier: Qualifier? = null,
    noinline viewModelDefinition: StateDefinition<T>
): Pair<Module, InstanceFactory<T>> {
    return factory(qualifier) {
        viewModelDefinition(getSavedState(it), it)
    }
}


inline fun <reified T> ScopeDSL.state(
    state: T?,
    qualifier: Qualifier = stateQualifier
) {
    state?.let { scoped(qualifier) { state } }
}

typealias StateDefinition<T> = Scope.(SavedState, ParametersHolder) -> T

val stateQualifier = named("saved_state_qualifier")
