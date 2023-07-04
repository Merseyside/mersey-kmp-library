package com.merseyside.merseyLib.utils.core.koin.scope.delegate

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.utils.fragment.onBackPressedDispatcher.onBackPressed
import com.merseyside.utils.fragment.onBackPressedDispatcher.setOnBackPressedCallback
import org.koin.core.Koin
import org.koin.core.scope.Scope

class OnBackPressedScopeDelegate(
    koin: Koin,
    private val fragment: Fragment,
    provideScope: (Koin) -> Scope?,
    createScope: (Koin) -> Scope
) : ScopeDelegate(koin, provideScope, createScope) {

    private lateinit var callback: OnBackPressedCallback

    override fun onScopeCreated(scope: Scope) {
        callback = fragment.setOnBackPressedCallback {
            callback.isEnabled = false
            fragment.onBackPressed()
            scope.close()
            Logger.log("Koin", "OnBackPressedScopeDelegate $scope closed")
        }
    }
}