package com.merseyside.merseyLib.utils.core.koin.scope.ext

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.koin.core.scope.Scope

internal fun LifecycleOwner.addLifecycleObserver(scope: Scope): Scope {

    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            scope.close()
        }
    })

    return scope
}