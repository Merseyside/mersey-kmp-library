package com.merseyside.merseyLib.archy.core.presentation.model

import com.merseyside.merseyLib.utils.core.SavedState


abstract class StateViewModel: BaseViewModel() {

    abstract fun onRestoreState(savedState: SavedState)
    abstract fun onSaveState(savedState: SavedState)

    companion object {
        const val INSTANCE_STATE_KEY = "instance_state"
    }
}