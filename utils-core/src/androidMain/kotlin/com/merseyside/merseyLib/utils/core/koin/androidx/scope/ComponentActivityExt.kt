package com.merseyside.merseyLib.utils.core.koin.androidx.scope

import android.content.ComponentCallbacks
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.lifecycle.LifecycleOwner
import com.merseyside.merseyLib.utils.core.koin.scope.provider.LifecycleScopeProvider
import com.merseyside.merseyLib.utils.core.koin.scope.provider.OnBackPressedScopeProvider
import org.koin.android.ext.android.getKoin
import org.koin.core.component.getScopeId
import org.koin.core.component.getScopeName
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeID


fun ComponentActivity.activityScope(
    scopeID: ScopeID = getScopeId(),
    qualifier: Qualifier = getScopeName(),
    source: Any? = null,
    modules: List<Module> = emptyList()
) = lazy { createActivityScope(scopeID, qualifier, source, modules) }

fun ComponentActivity.createActivityScope(
    scopeID: ScopeID = getScopeId(),
    qualifier: Qualifier = getScopeName(),
    source: Any? = null,
    modules: List<Module> = emptyList()
): Scope {
    return getOrCreateScopeForCurrentLifecycle(scopeID, qualifier, source, modules, this)
}

internal fun ComponentCallbacks.getOrCreateScopeForCurrentLifecycle(
    scopeID: ScopeID,
    qualifier: Qualifier,
    source: Any? = null,
    modules: List<Module>,
    owner: LifecycleOwner,
    onCreated: (Scope) -> Unit = {}
): Scope {

    val lifecycleScopeProvider = LifecycleScopeProvider(
        koin = getKoin(),
        lifecycleOwner = owner,
        modules = modules,
        provideScope = { koin -> koin.getScopeOrNull(scopeID) },
        createScope = { koin -> koin.createScope(scopeID, qualifier, source).also(onCreated) }
    )

    return lifecycleScopeProvider.getValue()
}


internal fun ComponentCallbacks.getOrCreateOnBackPressedScope(
    scopeID: ScopeID,
    qualifier: Qualifier,
    source: Any? = null,
    modules: List<Module>,
    onBackPressedDispatcher: OnBackPressedDispatcher,
    onBackPressed: () -> Unit,
    onCreated: (Scope) -> Unit,
): Scope {
    val onBackPressedScopeProvider = OnBackPressedScopeProvider(
        koin = getKoin(),
        onBackPressedDispatcher = onBackPressedDispatcher,
        modules = modules,
        provideScope = { koin -> koin.getScopeOrNull(scopeID) },
        createScope = { koin -> koin.createScope(scopeID, qualifier, source).also(onCreated) },
        onBackPressed = onBackPressed
    )

    return onBackPressedScopeProvider.getValue()
}