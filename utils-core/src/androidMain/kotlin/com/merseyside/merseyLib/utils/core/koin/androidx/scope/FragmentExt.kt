package com.merseyside.merseyLib.utils.core.koin.androidx.scope

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.merseyside.utils.fragment.onBackPressedDispatcher.onBackPressed
import org.koin.androidx.scope.getScopeOrNull
import org.koin.core.component.getScopeId
import org.koin.core.component.getScopeName
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeID

fun Fragment.fragmentScope(
    scopeID: ScopeID = getScopeId(),
    qualifier: Qualifier = getScopeName(),
    source: Any? = null,
    modules: List<Module> = emptyList(),
    useParentActivityScope: Boolean = true
) = lazy { createFragmentScope(scopeID, qualifier, source, modules, useParentActivityScope) }

fun Fragment.createFragmentScope(
    scopeID: ScopeID = getScopeId(),
    qualifier: Qualifier = getScopeName(),
    source: Any? = null,
    modules: List<Module> = emptyList(),
    useParentActivityScope: Boolean = true,
): Scope {
    return getOrCreateScopeForCurrentLifecycle(scopeID, qualifier, source, modules, this) { scope ->
        if (useParentActivityScope) {
            val activityScope = requireActivity().getScopeOrNull()
            if (activityScope != null) {
                scope.linkTo(activityScope)
            } else {
                scope.logger.debug("Fragment '$this' can't be linked to parent activity scope")
            }
        }
    }
}

fun Fragment.onBackPressedFragmentScope(
    scopeID: ScopeID = getScopeId(),
    qualifier: Qualifier = getScopeName(),
    source: Any? = null,
    modules: List<Module>,
    useParentActivityScope: Boolean = true
) = lazy {
    createOnBackPressedFragmentScope(scopeID, qualifier, source, modules, useParentActivityScope)
}

fun Fragment.createOnBackPressedFragmentScope(
    scopeID: ScopeID,
    qualifier: Qualifier,
    source: Any? = null,
    modules: List<Module>,
    useParentActivityScope: Boolean = true,
): Scope {
    return getOrCreateOnBackPressedScope(
        scopeID,
        qualifier,
        source,
        modules,
        requireActivity().onBackPressedDispatcher,
        onBackPressed = { findNavController().navigateUp() }
    ) { scope ->
        if (useParentActivityScope) {
            val activityScope = requireActivity().getScopeOrNull()
            if (activityScope != null) {
                scope.linkTo(activityScope)
            } else {
                scope.logger.debug("Fragment '$this' can't be linked to parent activity scope")
            }
        }
    }
}