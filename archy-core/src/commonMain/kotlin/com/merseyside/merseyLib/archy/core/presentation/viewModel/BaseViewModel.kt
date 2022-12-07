package com.merseyside.merseyLib.archy.core.presentation.viewModel

import com.merseyside.merseyLib.kotlin.coroutines.ext.mapState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

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

    fun <T, K> StateFlow<T>.mapState(
        transform: (data: T) -> K
    ): StateFlow<K> {
        return mapState(
            scope = viewModelScope,
            transform = transform
        )
    }

    fun <T, K> StateFlow<T>.mapState(
        initialValue: K,
        transform: suspend (data: T) -> K
    ): StateFlow<K> {
        return mapState(
            scope = viewModelScope,
            initialValue = initialValue,
            transform = transform
        )
    }

    fun <T, R> Flow<T>.mapState(
        initialValue: R,
        started: SharingStarted = SharingStarted.Eagerly,
        transform: suspend (data: T) -> R
    ): StateFlow<R> {
        return map(transform).stateIn(viewModelScope, started, initialValue)
    }

    fun <T1, T2, R> combineState(
        flow1: StateFlow<T1>,
        flow2: StateFlow<T2>,
        scope: CoroutineScope = viewModelScope,
        sharingStarted: SharingStarted = SharingStarted.Eagerly,
        transform: (T1, T2) -> R
    ): StateFlow<R> = combine(flow1, flow2) {
            o1, o2 -> transform.invoke(o1, o2)
    }.stateIn(scope, sharingStarted, transform.invoke(flow1.value, flow2.value))

    fun <T1, T2, T3, R> combineState(
        flow1: StateFlow<T1>,
        flow2: StateFlow<T2>,
        flow3: StateFlow<T3>,
        scope: CoroutineScope = viewModelScope,
        sharingStarted: SharingStarted = SharingStarted.Eagerly,
        transform: (T1, T2, T3) -> R
    ): StateFlow<R> = combine(flow1, flow2, flow3) {
            o1, o2, o3 -> transform.invoke(o1, o2, o3)
    }.stateIn(scope, sharingStarted, transform.invoke(flow1.value, flow2.value, flow3.value))

    fun <T1, T2, T3, T4, R> combineState(
        flow1: StateFlow<T1>,
        flow2: StateFlow<T2>,
        flow3: StateFlow<T3>,
        flow4: StateFlow<T4>,
        scope: CoroutineScope = viewModelScope,
        sharingStarted: SharingStarted = SharingStarted.Eagerly,
        transform: (T1, T2, T3, T4) -> R
    ): StateFlow<R> = combine(flow1, flow2, flow3, flow4) {
            o1, o2, o3, o4 -> transform.invoke(o1, o2, o3, o4)
    }.stateIn(scope, sharingStarted, transform.invoke(flow1.value, flow2.value, flow3.value, flow4.value))
}