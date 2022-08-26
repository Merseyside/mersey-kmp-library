package com.merseyside.merseyLib.archy.core.di

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

abstract class KoinModuleLoader(val createdAtStart: Boolean = false) {

    abstract val moduleDefinition: Module.() -> Unit

    private val module = module(createdAtStart, moduleDefinition)

    fun load() {
        loadKoinModules(module)
    }

    fun unload() {
        unloadKoinModules(module)
    }
}