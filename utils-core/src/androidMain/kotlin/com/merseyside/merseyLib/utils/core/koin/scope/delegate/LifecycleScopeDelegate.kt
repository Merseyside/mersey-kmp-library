package com.merseyside.merseyLib.utils.core.koin.scope.delegate

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.merseyside.merseyLib.kotlin.logger.Logger
import org.koin.core.Koin
import org.koin.core.scope.Scope

class LifecycleScopeDelegate(
    koin: Koin,
    val lifecycleOwner: LifecycleOwner,
    provideScope: (Koin) -> Scope?,
    createScope: (Koin) -> Scope
) : ScopeDelegate(koin, provideScope, createScope) {

    override fun onScopeCreated(scope: Scope) {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onDestroy(owner: LifecycleOwner) {
                koin.logger.debug("Closing scope: ${this@LifecycleScopeDelegate.scope} for $lifecycleOwner")
                if (!scope.closed) {
                    scope.close()
                }
                Logger.log("Koin", "Scope $scope closed")
                this@LifecycleScopeDelegate.scope = null
            }
        })
    }
}