package com.merseyside.merseyLib.archy.android.presentation.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.merseyLib.archy.android.presentation.extensions.getString
import com.merseyside.merseyLib.archy.core.presentation.viewModel.EventsViewModel
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.Alert
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.TextMessage
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
                title?.getString(this@VMEventsActivity),
                message?.getString(this@VMEventsActivity),
                positiveButtonText?.getString(this@VMEventsActivity),
                negativeButtonText?.getString(this@VMEventsActivity),
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

    override fun onConnectionStateChanged(state: Boolean) {}

    private fun showErrorMsg(textMessage: TextMessage) {
        with(textMessage) {
            actionMsg?.let {
                showErrorMsg(
                    msg.getString(this@VMEventsActivity),
                    null,
                    it.getString(this@VMEventsActivity),
                    onClick
                )
            } ?: run {
                showErrorMsg(msg.getString(this@VMEventsActivity))
            }
        }
    }

    private fun showMsg(textMessage: TextMessage) {
        with(textMessage) {
            actionMsg?.let {
                showMsg(
                    msg.getString(this@VMEventsActivity),
                    null,
                    it.getString(this@VMEventsActivity),
                    onClick
                )
            } ?: run {
                showMsg(msg.getString(this@VMEventsActivity))
            }
        }
    }
}