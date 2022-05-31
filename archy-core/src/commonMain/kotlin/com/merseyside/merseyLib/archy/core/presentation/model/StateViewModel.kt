package com.merseyside.merseyLib.archy.core.presentation.model

import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.merseyLib.utils.core.state.StateSaver

abstract class StateViewModel: BaseViewModel(), StateSaver {

    abstract val savedState: SavedState

    companion object {
        const val INSTANCE_STATE_KEY = "instance_state"
    }
}