package com.merseyside.merseyLib.archy.core.di.moduleLoader

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.ModuleDeclaration

class CoroutineScopeKoinModuleLoader internal constructor(
    createdAtStart: Boolean = false,
    private val scopeQualifier: Qualifier? = null,
    private val coroutineScope: () -> CoroutineScope,
    moduleDeclaration: ModuleDeclaration
) : KoinModuleLoader(createdAtStart, moduleDeclaration) {

    private var currentScope: CoroutineScope? = null

    init {
        declarations.add {
            single(scopeQualifier) { coroutineScope().also {
                currentScope = it
            }}
        }
    }

    override fun unload() {
        currentScope?.run {
            cancel("Scope canceled because module unloaded")
            currentScope = null
        }
        super.unload()
    }
}

fun coroutineScopeModuleLoader(
    createdAtStart: Boolean = false,
    scopeQualifier: Qualifier? = null,
    coroutineScope: () -> CoroutineScope,
    moduleDeclaration: ModuleDeclaration
): CoroutineScopeKoinModuleLoader {
    return CoroutineScopeKoinModuleLoader(
        createdAtStart,
        scopeQualifier,
        coroutineScope,
        moduleDeclaration
    )
}