package com.merseyside.merseyLib.utils.core.koin.moduleLoader

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools

open class KoinModuleLoader(
    private val createdAtStart: Boolean = false,
    moduleDeclaration: ModuleDeclaration
) {

    var isLoaded: Boolean = false
    protected val declarations: MutableList<ModuleDeclaration> = mutableListOf(moduleDeclaration)

    lateinit var currentModule: Module

    open fun load() {
        currentModule = createModule(createdAtStart)
        loadKoinModules(currentModule)
        KoinPlatformTools.defaultContext().get().createEagerInstances()
        isLoaded = true
    }

    open fun unload() {
        if (isLoaded) {
            unloadKoinModules(currentModule)
            isLoaded = false
        }
    }

    private fun createModule(
        createdAtStart: Boolean
    ): Module {
        return module(createdAtStart) {
            declarations.forEach { declaration -> declaration() }
        }
    }
}