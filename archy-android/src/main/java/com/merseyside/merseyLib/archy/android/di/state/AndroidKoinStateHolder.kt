package com.merseyside.merseyLib.archy.android.di.state

import android.os.Bundle
import com.merseyside.merseyLib.archy.core.di.state.KoinStateHolder
import com.merseyside.merseyLib.archy.core.di.state.toSavedState
import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.merseyLib.archy.core.di.state.toBundle
import org.koin.android.scope.AndroidScopeComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

interface AndroidKoinScopeState : AndroidScopeComponent {

    fun initKoinState(outState: Bundle?, toScope: Qualifier) {
        loadKoinModules(getKoinStateModule(outState, toScope))
    }

    fun saveKoinState(outState: Bundle) {
        val koinState = getKoinStateHolder().performSaveState()
        outState.putBundle(koinStateKey, koinState.toBundle())
    }

    private fun getKoinSavedState(outState: Bundle?): SavedState {
        return outState?.getBundle(koinStateKey).toSavedState()
    }

    private fun getKoinStateModule(outState: Bundle?, toScope: Qualifier) = module {
        scope(toScope) {
            scoped { KoinStateHolder(getKoin(), getKoinSavedState(outState)) }
        }
    }

//    private fun getKoinStateScope(): Scope {
//        return getKoin().getOrCreateScope(
//            scopeId = linkableScope.id,
//            qualifier = qualifier
//        )
//    }

    fun getKoinStateHolder(): KoinStateHolder = scope.get()

}

private const val koinStateKey = "koin_state"
