package com.merseyside.merseyLib.archy.android.di

import androidx.fragment.app.Fragment
import com.merseyside.merseyLib.utils.core.koin.SharedScopeHolder
import org.koin.core.scope.ScopeID

fun Fragment.findParentSharedScopeHolder(scopeID: ScopeID? = null): SharedScopeHolder? {
    val parent = parentFragment

    return if (parent != null) {
        if (parent !is SharedScopeHolder) {
            parent.findParentSharedScopeHolder(scopeID)
        } else if (scopeID != null) {
            if (parent.scopeID != scopeID) {
                parent.findParentSharedScopeHolder(scopeID)
            } else parent
        } else parent
    } else null
}

fun Fragment.requireParentSharedScopeHolder(scopeID: ScopeID): SharedScopeHolder {
    return findParentSharedScopeHolder(scopeID) ?: throw NullPointerException("Scope required but returned null!")
}