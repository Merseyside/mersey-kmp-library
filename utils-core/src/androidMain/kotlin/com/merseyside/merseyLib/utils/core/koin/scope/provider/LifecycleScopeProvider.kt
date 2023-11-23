package com.merseyside.merseyLib.utils.core.koin.scope.provider

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.merseyside.merseyLib.kotlin.logger.Logger
import org.koin.android.scope.AndroidScopeComponent
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.core.scope.Scope

class LifecycleScopeProvider(
    koin: Koin,
    val lifecycleOwner: LifecycleOwner,
    modules: List<Module>,
    provideScope: (Koin) -> Scope?,
    createScope: (Koin) -> Scope
) : ScopeProvider(koin, modules, provideScope, createScope) {
    override fun onScopeClosed(scope: Scope) {
        (lifecycleOwner as AndroidScopeComponent).onCloseScope()
        Logger.log("Koin", "Scope $scope closed")
    }

    override fun onScopeCreated(scope: Scope) {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onDestroy(owner: LifecycleOwner) {
                if (!scope.closed) {
                    close()
                }
            }
        })
    }
}