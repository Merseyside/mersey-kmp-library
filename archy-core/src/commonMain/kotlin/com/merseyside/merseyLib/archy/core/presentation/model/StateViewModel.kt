package com.merseyside.merseyLib.archy.core.presentation.model

import com.merseyside.merseyLib.utils.core.state.DummySavedState
import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.merseyLib.utils.core.state.StateSaver


abstract class StateViewModel(
    override val savedState: SavedState = DummySavedState()
): BaseViewModel(), StateSaver {

    companion object {
        const val INSTANCE_STATE_KEY = "instance_state"
    }
}