package com.merseyside.merseyLib.archy.core.presentation.model

import com.merseyside.merseyLib.utils.core.Logger
import com.merseyside.merseyLib.utils.core.ext.getString
import com.merseyside.merseyLib.utils.core.ext.getStringNull
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource

abstract class BaseViewModel protected constructor() : ViewModel() {

    val isInProgress = MutableLiveData(false)
    val progressText = MutableLiveData<String?>(null)

    val errorLiveEvent: MutableLiveData<Throwable?> =
        MutableLiveData(null)

    val messageLiveEvent: MutableLiveData<TextMessage?> =
        MutableLiveData(null)

    val isInProgressLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    val alertDialogLiveEvent: MutableLiveData<AlertDialogModel?> =
        MutableLiveData(null)

    val grantPermissionLiveEvent: MutableLiveData<Pair<Array<String>, Int>?> =
        MutableLiveData(null)

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

    open fun handleError(throwable: Throwable) {
        errorLiveEvent.value = throwable
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

        messageLiveEvent.value = textMessage
    }

    protected fun showErrorMsg(msg: String) {
        Logger.logErr(this, msg)
        val textMessage =
            TextMessage(
                isError = true,
                msg = msg
            )

        messageLiveEvent.value = textMessage
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

        messageLiveEvent.value = textMessage
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

        messageLiveEvent.value = textMessage
    }

    open fun onError(throwable: Throwable) {}


    fun showProgress(text: String? = null) {
        Logger.log(this, text ?: "Empty")

        isInProgress.value = true
        progressText.value = text

        isInProgressLiveData.value = true
    }

    fun hideProgress() {
        if (isInProgressLiveData.value) {
            isInProgress.value = false
            progressText.value = null

            isInProgressLiveData.value = false
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
        alertDialogLiveEvent.value =
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

    open fun onBack() : Boolean {
        return true
    }
}