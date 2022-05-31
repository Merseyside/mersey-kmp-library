package com.merseyside.merseyLib.archy.core.presentation.model

import com.merseyside.merseyLib.kotlin.Logger
import com.merseyside.merseyLib.kotlin.coroutines.ext.mapState
import com.merseyside.merseyLib.utils.core.ext.getString
import com.merseyside.merseyLib.utils.core.ext.getStringNull
import com.merseyside.merseyLib.utils.core.mvvm.MutableSingleEvent
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel protected constructor() : ViewModel() {

    internal val scope: CoroutineScope
        get() { return viewModelScope }

    private val mutProgress = MutableLiveData(false)
    val isInProgress: LiveData<Boolean> = mutProgress

    protected var progress: Boolean
        get() = mutProgress.value
        set(value) {
            mutProgress.value = value
        }

    private val mutProgressText = MutableSingleEvent<String?>(null)
    val progressText: LiveData<String?> = mutProgressText

    private val mutErrorLiveEvent = MutableSingleEvent<Throwable?>(null)
    val errorLiveEvent: LiveData<Throwable?> = mutErrorLiveEvent

    private val mutMessageLiveEvent = MutableSingleEvent<TextMessage?>(null)
    val messageLiveEvent: LiveData<TextMessage?> = mutMessageLiveEvent

    private val mutAlertDialogLiveEvent = MutableSingleEvent<AlertDialogModel?>(null)
    val alertDialogLiveEvent: LiveData<AlertDialogModel?> = mutAlertDialogLiveEvent

    private val mutGrantPermissionLiveEvent = MutableSingleEvent<Pair<Array<String>, Int>?>(null)
    val grantPermissionLiveEvent: LiveData<Pair<Array<String>, Int>?> = mutGrantPermissionLiveEvent

    private val mutQueryRestoreEvent = MutableSingleEvent<String?>(null)
    val queryRestoreEvent: LiveData<String?> = mutQueryRestoreEvent

    data class TextMessage(
        val isError: Boolean = false,
        var msg: String = "",
        var actionMsg: String? = null,
        val onClick: () -> Unit = {}
    )

    data class AlertDialogModel(
        val title: String? = null,
        val message: String? = null,
        val positiveButtonText: String? = null,
        val negativeButtonText: String? = null,
        val onPositiveClick: () -> Unit = {},
        val onNegativeClick: () -> Unit = {},
        val isSingleAction: Boolean? = null,
        val isCancelable: Boolean? = null
    )

    /**
     * @return true if
     * @param throwable have been handled
     **/
    open fun handleError(throwable: Throwable): Boolean {
        mutErrorLiveEvent.value = throwable
        return true
    }

    protected fun showMsg(id: StringResource, vararg args: String) {
        showMsg(getString(id, *args))
    }

    protected fun showErrorMsg(id: StringResource, vararg args: String) {
        showErrorMsg(getString(id, *args))
    }

    protected fun showMsg(msg: String) {
        Logger.log(this, msg)
        val textMessage = TextMessage(
            isError = false,
            msg = msg
        )

        mutMessageLiveEvent.value = textMessage
    }

    protected fun showErrorMsg(msg: String) {
        Logger.logErr(this, msg)
        val textMessage =
            TextMessage(
                isError = true,
                msg = msg
            )

        mutMessageLiveEvent.value = textMessage
    }

    protected fun showMsg(msg: String, actionMsg: String, onClick: () -> Unit = {}) {
        Logger.log(this, msg)
        val textMessage =
            TextMessage(
                isError = false,
                msg = msg,
                actionMsg = actionMsg,
                onClick = onClick
            )

        mutMessageLiveEvent.value = textMessage
    }

    protected fun showErrorMsg(msg: String, actionMsg: String, onClick: () -> Unit = {}) {
        Logger.logErr(this, msg)
        val textMessage =
            TextMessage(
                isError = true,
                msg = msg,
                actionMsg = actionMsg,
                onClick = onClick
            )

        mutMessageLiveEvent.value = textMessage
    }

    open fun onError(throwable: Throwable) {}


    fun showProgress(text: String? = null) {
        Logger.log(this, text ?: "Empty")

        progress = true
        mutProgressText.value = text

        progress = true
    }

    fun hideProgress() {
        if (progress) {
            progress = false
            mutProgressText.value = null

            progress = false
        }
    }

    fun showAlertDialog(
        title: String? = null,
        message: String? = null,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        onPositiveClick: () -> Unit = {},
        onNegativeClick: () -> Unit = {},
        isSingleAction: Boolean? = null,
        isCancelable: Boolean? = null
    ) {
        mutAlertDialogLiveEvent.value =
            AlertDialogModel(
                title,
                message,
                positiveButtonText,
                negativeButtonText,
                onPositiveClick,
                onNegativeClick,
                isSingleAction,
                isCancelable
            )
    }

    fun showAlertDialog(
        titleRes: StringResource? = null,
        messageRes: StringResource? = null,
        positiveButtonTextRes: StringResource? = null,
        negativeButtonTextRes: StringResource? = null,
        onPositiveClick: () -> Unit = {},
        onNegativeClick: () -> Unit = {},
        isSingleAction: Boolean? = null,
        isCancelable: Boolean? = null
    ) {

        showAlertDialog(
            getStringNull(titleRes),
            getStringNull(messageRes),
            getStringNull(positiveButtonTextRes),
            getStringNull(negativeButtonTextRes),
            onPositiveClick,
            onNegativeClick,
            isSingleAction,
            isCancelable
        )
    }

    fun restoreQuery(query: String) {
        mutQueryRestoreEvent.value = query
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