package com.merseyside.merseyLib.utils.core.koin.scope.ext

import androidx.fragment.app.Fragment
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.ScopeID

fun Fragment.sharedScope(
    scopeID: ScopeID,
    qualifier: Qualifier
): LifecycleSharedScopeDelegate {
    return sharedScope(lazy { scopeID }, lazy { qualifier })
}

fun Fragment.sharedScope(
    scopeID: Lazy<ScopeID>,
    qualifier: Lazy<Qualifier>
): LifecycleSharedScopeDelegate {
    return LifecycleSharedScopeDelegate(
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