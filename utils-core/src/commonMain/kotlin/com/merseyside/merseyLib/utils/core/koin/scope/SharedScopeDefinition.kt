package com.merseyside.merseyLib.utils.core.koin.scope

import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.ScopeID

interface SharedScopeDefinition {

    val scopeID: ScopeID
    val qualifier: Qualifier
}