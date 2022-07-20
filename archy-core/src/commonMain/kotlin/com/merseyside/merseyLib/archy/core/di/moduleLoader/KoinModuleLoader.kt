package com.merseyside.merseyLib.archy.core.di.moduleLoader

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

open class KoinModuleLoader internal constructor(
    private val createdAtStart: Boolean = false,
    moduleDeclaration: ModuleDeclaration
) {

    protected val declarations: MutableList<ModuleDeclaration> = mutableListOf(moduleDeclaration)

    lateinit var currentModule: Module

    open fun load() {
        currentModule = createModule(createdAtStart)
        loadKoinModules(currentModule)
    }

    open fun unload() {
        unloadKoinModules(currentModule)
    }

    private fun createModule(
        createdAtStart: Boolean
    ): Module {
        return module(createdAtStart) {
            declarations.forEach { declaration -> declaration() }
        }
    }
}

fun moduleLoader(
    createdAtStart: Boolean = false,
    moduleDeclaration: ModuleDeclaration
): KoinModuleLoader {
    return KoinModuleLoader(createdAtStart, moduleDeclaration)
}