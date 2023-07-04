package com.merseyside.merseyLib.utils.core.koin.scope.activity

import androidx.activity.ComponentActivity
import com.merseyside.merseyLib.utils.core.koin.scope.delegate.LifecycleScopeDelegate
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.ScopeID

fun ComponentActivity.fragmentScope(
    scopeID: Lazy<ScopeID>,
    qualifier: Lazy<Qualifier>
): LifecycleScopeDelegate {
    return LifecycleScopeDelegate(
        koin = getKoin(),
        lifecycleOwner = this,
        provideScope = { koin ->
            koin.getScopeOrNull(scopeID.value)
        },
        createScope = { koin ->
            koin.createScope(scopeID.value, qualifier.value, this)
        }
    )
}

fun ComponentActivity.fragmentScope(
    scopeID: ScopeID,
    qualifier: Qualifier
): LifecycleScopeDelegate {
    return fragmentScope(lazy {scopeID}, lazy {qualifier})
}