package com.merseyside.merseyLib.utils.core.state

interface StateSaver {
    val savedState: SavedState
    fun onSaveState(savedState: SavedState)
}