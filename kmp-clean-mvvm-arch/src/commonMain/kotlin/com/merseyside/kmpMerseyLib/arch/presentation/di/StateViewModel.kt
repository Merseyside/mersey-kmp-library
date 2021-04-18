package com.merseyside.kmpMerseyLib.arch.presentation.di

import com.merseyside.kmpMerseyLib.utils.SavedState


abstract class StateViewModel: BaseViewModel() {

    abstract fun onRestoreState(savedState: SavedState)
    abstract fun onSaveState(savedState: SavedState)

    companion object {
        const val INSTANCE_STATE_KEY = "instance_state"
    }
}