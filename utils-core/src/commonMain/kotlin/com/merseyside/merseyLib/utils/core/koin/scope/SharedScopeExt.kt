package com.merseyside.merseyLib.utils.core.koin.scope

import org.koin.core.Koin
import org.koin.core.scope.Scope

fun Koin.getSharedScopeOrNull(definition: SharedScopeDefinition): Scope? {
    return with(definition) {
        getScopeOrNull(scopeId = scopeID)
    }
}

fun Koin.createSharedScope(definition: SharedScopeDefinition): Scope {
    return with(definition) {
        getOrCreateScope(scopeId = scopeID, qualifier = qualifier)
    }
}