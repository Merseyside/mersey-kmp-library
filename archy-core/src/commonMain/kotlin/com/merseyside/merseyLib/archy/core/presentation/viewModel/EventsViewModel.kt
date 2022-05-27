package com.merseyside.merseyLib.archy.core.presentation.viewModel

import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.Alert
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.TextMessage
import com.merseyside.merseyLib.kotlin.Logger
import com.merseyside.merseyLib.utils.core.ext.getString
import com.merseyside.merseyLib.utils.core.ext.getStringNull
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.StringResource

abstract class EventsViewModel : NewBaseViewModel() {

    abstract val eventsDispatcher: EventsDispatcher<out BaseEventsListener>

    open fun handleError(throwable: Throwable): Boolean {
        eventsDispatcher.dispatchEvent {
            if (!onError(throwable)) throw Exception("Error not handled!")
        }
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
        TextMessage(
            isError = false,
            msg = msg
        ).also { showMessage(it) }
    }

    protected fun showErrorMsg(msg: String) {
        Logger.logErr(this, msg)
        TextMessage(
            isError = true,
            msg = msg
        ).also { showMessage(it) }
    }

    protected fun showMsg(msg: String, actionMsg: String, onClick: () -> Unit = {}) {
        Logger.log(this, msg)
        TextMessage(
            isError = false,
            msg = msg,
            actionMsg = actionMsg,
            onClick = onClick
        ).also { showMessage(it) }
    }

    protected fun showErrorMsg(msg: String, actionMsg: String, onClick: () -> Unit = {}) {
        Logger.logErr(this, msg)
        TextMessage(
            isError = true,
            msg = msg,
            actionMsg = actionMsg,
            onClick = onClick
        ).also { showMessage(it) }
    }

    fun showAlert(
        title: String? = null,
        message: String? = null,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        onPositiveClick: () -> Unit = {},
        onNegativeClick: () -> Unit = {},
        isSingleAction: Boolean? = null,
        isCancelable: Boolean? = null
    ) {
        Alert(
            title,
            message,
            positiveButtonText,
            negativeButtonText,
            onPositiveClick,
            onNegativeClick,
            isSingleAction,
            isCancelable
        ).also { showAlert(it) }
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
        showAlert(
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

    protected fun showMessage(message: TextMessage) {
        eventsDispatcher.dispatchEvent { onMessage(message) }
    }

    protected fun showAlert(alert: Alert) {
        eventsDispatcher.dispatchEvent { onAlert(alert) }
    }

    interface BaseEventsListener {
        fun onError(throwable: Throwable): Boolean
        fun onMessage(message: TextMessage)
        fun onAlert(alert: Alert)
    }
}