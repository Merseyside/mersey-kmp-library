package com.merseyside.merseyLib.utils.core.state

interface StateSaver {
    val savedState: SavedState?

    @Deprecated("Pass saved state in constructor")
    fun onRestoreState(restoredState: SavedState)
    fun onSaveState(savedState: SavedState)

}