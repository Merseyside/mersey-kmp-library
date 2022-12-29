package com.merseyside.merseyLib.utils.core.koin

import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeID

interface SharedScopeHolder {

    val sharedScope: Scope
    val scopeID: ScopeID
}