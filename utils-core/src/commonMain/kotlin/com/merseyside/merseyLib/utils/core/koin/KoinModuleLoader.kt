package com.merseyside.merseyLib.utils.core.koin

import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class KoinModuleLoader(private val createdAtStart: Boolean = false) {

    abstract val moduleDefinition: Module.() -> Unit

    private val module by lazy { module(createdAtStart, moduleDefinition) }

    fun load() {
        loadKoinModules(module)
    }

    fun unload() {
        unloadKoinModules(module)
    }
}