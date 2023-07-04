package com.merseyside.merseyLib.utils.core.koin.savedState.scope

import androidx.fragment.app.Fragment
import com.merseyside.merseyLib.utils.core.koin.savedState.provider.StateProvider
import org.koin.core.scope.Scope

context(Fragment)
fun Scope.declareStateProvider() {
    declare(StateProvider(savedStateRegistry))
}