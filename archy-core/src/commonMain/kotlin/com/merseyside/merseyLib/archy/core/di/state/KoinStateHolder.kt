package com.merseyside.merseyLib.archy.core.di.state

import com.merseyside.merseyLib.utils.core.state.DummySavedState
import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.merseyLib.utils.core.state.StateSaver
import org.koin.core.Koin
import org.koin.core.logger.Level

class KoinStateHolder(val koin: Koin, val rootSavedState: SavedState) {

    private val stateSavers: MutableList<StateSaver> = mutableListOf()

    fun addStateSaver(stateSaver: StateSaver) {
        stateSavers.add(stateSaver)
        koin.logger.log(Level.DEBUG) { " +++ Add $stateSaver state saver" }
    }

    fun performSaveState(): SavedState {
        stateSavers.forEach { saver ->
            val newState = SavedState().apply { saver.onSaveState(this) }
            rootSavedState.put(adoptStateKey(saver), newState)
            koin.logger.log(Level.DEBUG) { " +++ Add to saved state by $saver: $newState" }
        }

        return rootSavedState
    }

    inline fun <reified T> getSavedState(key: String = getStateKey<T>()): SavedState {
        koin.logger.log(Level.DEBUG) { " +++ Getting saved state by key $key" }
        return (rootSavedState.getSavedState(key) ?: DummySavedState()).also {
            koin.logger.log(Level.DEBUG) { " Result is $it" }
        }
    }

    companion object {
        fun adoptStateKey(obj: Any): String {
            return getStateKey(obj::class)
        }
    }

}