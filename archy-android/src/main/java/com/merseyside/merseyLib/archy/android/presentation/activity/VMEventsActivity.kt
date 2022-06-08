package com.merseyside.merseyLib.archy.android.presentation.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.merseyLib.archy.core.presentation.viewModel.BaseViewModel
import com.merseyside.merseyLib.archy.core.presentation.viewModel.EventsViewModel
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.Alert
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.TextMessage
import com.merseyside.merseyLib.kotlin.extensions.isNotNullAndEmpty
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain

abstract class VMEventsActivity<B : ViewDataBinding, Model, Listener>
    : VMActivity<B, Model>(), EventsViewModel.BaseEventsListener
        where Model : EventsViewModel,
              Listener : EventsViewModel.BaseEventsListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        (viewModel as EventsDispatcherOwner<Listener>).eventsDispatcher.bind(
            this,
            this as Listener
        )
    }

    override fun provideViewModel(bundle: Bundle?, vararg params: Any): Model {
        return super.provideViewModel(
            bundle,
            *params,
            eventsDispatcherOnMain<EventsDispatcher<Listener>>()
        )
    }

    override fun onAlert(alert: Alert) {
        with(alert) {
            showAlertDialog(
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
    }

    override fun onError(throwable: Throwable): Boolean {
        return this.handleError(throwable)
    }

    override fun onMessage(message: TextMessage) {
        if (message.isError) {
            showErrorMsg(message)
        } else {
            showMsg(message)
        }
    }

    private fun showErrorMsg(textMessage: TextMessage) {
        with(textMessage) {
            if (actionMsg.isNotNullAndEmpty()) {
                showErrorMsg(textMessage.msg, null, actionMsg, textMessage.onClick)
            } else {
                showErrorMsg(textMessage.msg)
            }
        }
    }

    private fun showMsg(textMessage: TextMessage) {
        with(textMessage) {
            if (actionMsg.isNotNullAndEmpty()) {
                showMsg(textMessage.msg, null, actionMsg, textMessage.onClick)
            } else {
                showMsg(textMessage.msg)
            }
        }
    }
}