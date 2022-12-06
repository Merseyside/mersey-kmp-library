package com.merseyside.merseyLib.utils.core.koin.moduleLoader

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.ModuleDeclaration

class CoroutineScopeKoinModuleLoader(
    private val scopeQualifier: Qualifier,
    val coroutineScope: CoroutineScope,
    createdAtStart: Boolean = false,
    moduleDeclaration: ModuleDeclaration
) : KoinModuleLoader(createdAtStart, moduleDeclaration) {

    init {
        declarations.add {
            single(scopeQualifier) { coroutineScope }
        }
    }

    override fun unload() {
        coroutineScope.cancel("Scope canceled because module unloaded")
        super.unload()
    }
}