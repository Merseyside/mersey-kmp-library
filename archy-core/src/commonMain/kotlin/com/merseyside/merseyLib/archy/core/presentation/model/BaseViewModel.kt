package com.merseyside.merseyLib.archy.core.presentation.model

import com.merseyside.merseyLib.kotlin.Logger
import com.merseyside.merseyLib.utils.core.ext.getString
import com.merseyside.merseyLib.utils.core.ext.getStringNull
import com.merseyside.merseyLib.utils.core.mvvm.MutableSingleEvent
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource

abstract class BaseViewModel protected constructor() : ViewModel() {

    private val mutProgress = MutableSingleEvent(false)
    val isInProgress: LiveData<Boolean> = mutProgress

    protected var progress: Boolean
        get() {
            return mutProgress.value
        }
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
        val textMessage =
            TextMessage(
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
}