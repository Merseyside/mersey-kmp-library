package com.merseyside.merseyLib.utils.core.state

interface InstanceStateManager<Instance> {
    fun saveState(savedState: SavedState, instance: Instance)
    fun restoreState(savedState: SavedState): Instance
    fun hasSavedState(savedState: SavedState): Boolean
}