package com.merseyside.merseyLib.utils.core.koin.scope.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.merseyside.merseyLib.utils.core.koin.scope.SharedScopeDefinition
import com.merseyside.merseyLib.utils.core.koin.scope.createSharedScope
import com.merseyside.merseyLib.utils.core.koin.scope.getSharedScopeOrNull
import org.koin.android.ext.android.getKoin
import org.koin.core.scope.Scope

fun Fragment.getOrCreateSharedScope(definition: SharedScopeDefinition): Scope {
    with(getKoin()) {
        return getSharedScopeOrNull(definition) ?: createSharedScope(definition).apply {
            addLifecycleObserver(this)
        }
    }
}

fun Fragment.getOrCreateSharedScope(): Scope {
    if (this !is SharedScopeDefinition) {
        throw IllegalArgumentException("Implement SharedScopeDefinition or pass it as argument explicitly!")
    }

    return getOrCreateSharedScope(this as SharedScopeDefinition)
}

internal fun LifecycleOwner.addLifecycleObserver(scope: Scope): Scope {

    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            scope.close()
        }
    })

    return scope
}