package com.merseyside.merseyLib.utils.core.koin.scope.provider

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import com.merseyside.merseyLib.kotlin.logger.Logger
import org.koin.core.Koin
import org.koin.core.module.Module
import org.koin.core.scope.Scope

class OnBackPressedScopeProvider(
    koin: Koin,
    private val onBackPressedDispatcher: OnBackPressedDispatcher,
    modules: List<Module>,
    provideScope: (Koin) -> Scope?,
    createScope: (Koin) -> Scope,
    private val onBackPressed: () -> Unit
) : ScopeProvider(koin, modules, provideScope, createScope) {

    private lateinit var callback: OnBackPressedCallback

    override fun onScopeCreated(scope: Scope) {
        callback = onBackPressedDispatcher.addCallback {
            callback.isEnabled = false
            onBackPressed()
            //close()
        }
    }

    override fun onScopeClosed(scope: Scope) {
        //callback.remove()
        Logger.log("Koin", "OnBackPressedScopeDelegate $scope closed")
    }
}