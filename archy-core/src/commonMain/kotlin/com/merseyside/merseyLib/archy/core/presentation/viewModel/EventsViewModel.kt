package com.merseyside.merseyLib.archy.core.presentation.viewModel

import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.Alert
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.TextMessage
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.TextValue
import com.merseyside.merseyLib.kotlin.logger.Logger
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.desc.StringDesc

abstract class EventsViewModel : BaseViewModel() {

    abstract val eventsDispatcher: EventsDispatcher<out BaseEventsListener>

    open fun handleError(throwable: Throwable): Boolean {
        eventsDispatcher.dispatchEvent {
            if (!onError(throwable)) throw Exception("Error not handled!")
        }
        return true
    }

    protected fun showMsg(msg: StringDesc) {
        TextMessage(
            isError = false,
            msg = TextValue(msg)
        ).also { showMessage(it) }
    }

    protected fun showErrorMsg(msg: StringDesc) {
        TextMessage(
            isError = true,
            msg = TextValue(msg)
        ).also { showMessage(it) }
    }

    protected fun showMsg(msg: String) {
        Logger.log(this, msg)
        TextMessage(
            isError = false,
            msg = TextValue(msg)
        ).also { showMessage(it) }
    }

    protected fun showErrorMsg(msg: String) {
        Logger.logErr(this, msg)
        TextMessage(
            isError = true,
            msg = TextValue(msg)
        ).also { showMessage(it) }
    }

    protected fun showMsg(msg: String, actionMsg: String, onClick: () -> Unit = {}) {
        Logger.log(this, msg)
        TextMessage(
            isError = false,
            msg = TextValue(msg),
            actionMsg = TextValue(actionMsg),
            onClick = onClick
        ).also { showMessage(it) }
    }

    protected fun showErrorMsg(msg: String, actionMsg: String, onClick: () -> Unit = {}) {
        Logger.logErr(this, msg)
        TextMessage(
            isError = true,
            msg = TextValue(msg),
            actionMsg = TextValue(actionMsg),
            onClick = onClick
        ).also { showMessage(it) }
    }

    protected fun showMsg(msg: StringDesc, actionMsg: StringDesc, onClick: () -> Unit = {}) {
        Logger.log(this, msg)
        TextMessage(
            isError = false,
            msg = TextValue(msg),
            actionMsg = TextValue(actionMsg),
            onClick = onClick
        ).also { showMessage(it) }
    }

    protected fun showErrorMsg(msg: StringDesc, actionMsg: String, onClick: () -> Unit = {}) {
        Logger.logErr(this, msg)
        TextMessage(
            isError = true,
            msg = TextValue(msg),
            actionMsg = TextValue(actionMsg),
            onClick = onClick
        ).also { showMessage(it) }
    }

    protected fun showErrorMsg(msg: String, actionMsg: StringDesc, onClick: () -> Unit = {}) {
        Logger.logErr(this, msg)
        TextMessage(
            isError = true,
            msg = TextValue(msg),
            actionMsg = TextValue(actionMsg),
            onClick = onClick
        ).also { showMessage(it) }
    }

    protected fun showErrorMsg(msg: StringDesc, actionMsg: StringDesc, onClick: () -> Unit = {}) {
        Logger.logErr(this, msg)
        TextMessage(
            isError = true,
            msg = TextValue(msg),
            actionMsg = TextValue(actionMsg),
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
            title?.let { TextValue(it) },
            message?.let { TextValue(it) },
            positiveButtonText?.let { TextValue(it) },
            negativeButtonText?.let { TextValue(it) },
            onPositiveClick,
            onNegativeClick,
            isSingleAction,
            isCancelable
        ).also { showAlert(it) }
    }

    fun showAlertDialog(
        title: StringDesc? = null,
        message: StringDesc? = null,
        positiveButtonText: StringDesc? = null,
        negativeButtonText: StringDesc? = null,
        onPositiveClick: () -> Unit = {},
        onNegativeClick: () -> Unit = {},
        isSingleAction: Boolean? = null,
        isCancelable: Boolean? = null
    ) {
        Alert(
            title?.let { TextValue(it) },
            message?.let { TextValue(it) },
            positiveButtonText?.let { TextValue(it) },
            negativeButtonText?.let { TextValue(it) },
            onPositiveClick,
            onNegativeClick,
            isSingleAction,
            isCancelable
        ).also { showAlert(it) }
    }

    protected fun showMessage(message: TextMessage) {
        eventsDispatcher.dispatchEvent { onMessage(message) }
    }

    protected fun showAlert(alert: Alert) {
        eventsDispatcher.dispatchEvent { onAlert(alert) }
    }

    protected open fun dispatchConnectionStateEvent(state: Boolean) {
        eventsDispatcher.dispatchEvent { onConnectionStateChanged(state) }
    }

    interface BaseEventsListener {
        fun onError(throwable: Throwable): Boolean
        fun onMessage(message: TextMessage)
        fun onAlert(alert: Alert)
        fun onConnectionStateChanged(state: Boolean)
    }
}