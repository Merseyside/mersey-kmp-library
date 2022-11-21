package com.merseyside.merseyLib.utils.core.koin.scope.ext

import androidx.activity.ComponentActivity
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.ScopeID

fun ComponentActivity.sharedScope(
    scopeID: Lazy<ScopeID>,
    qualifier: Lazy<Qualifier>
): LifecycleSharedScopeDelegate {
    return LifecycleSharedScopeDelegate(getKoin(), this) { koin ->
        koin.getScopeOrNull(scopeID.value) ?: koin.createScope(scopeID.value, qualifier.value, this)
    }
}

fun ComponentActivity.sharedScope(
    scopeID: ScopeID,
    qualifier: Qualifier
): LifecycleSharedScopeDelegate {
    return sharedScope(lazy {scopeID}, lazy {qualifier})
}