package com.merseyside.merseyLib.archy.core.presentation.viewModel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel protected constructor() : ViewModel() {

    private val mutProgress = MutableStateFlow(false)
    val isInProgress = mutProgress.asStateFlow()

    private val mutProgressText = MutableStateFlow<String?>(null)
    val progressText = mutProgressText.asStateFlow()

    protected var progress: Boolean
        get() = mutProgress.value
        set(value) {
            mutProgress.value = value
        }

    open fun onError(throwable: Throwable): Boolean = false


    fun showProgress(text: String? = null) {
        viewModelScope
        mutProgressText.value = text
        progress = true
    }

    fun hideProgress() {
        if (progress) {
            mutProgressText.value = null
            progress = false
        }
    }

    open fun onBack(): Boolean {
        return true
    }
}