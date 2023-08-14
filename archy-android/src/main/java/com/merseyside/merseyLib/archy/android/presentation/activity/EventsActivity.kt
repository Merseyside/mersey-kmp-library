package com.merseyside.merseyLib.archy.android.presentation.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.merseyLib.archy.android.presentation.extensions.getString
import com.merseyside.merseyLib.archy.core.presentation.message.Message
import com.merseyside.merseyLib.archy.core.presentation.message.TypedMessage
import com.merseyside.merseyLib.archy.core.presentation.viewModel.EventsViewModel
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.Alert
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.TextMessage
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain

abstract class EventsActivity<B : ViewDataBinding, Model, Listener>
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

    override fun performInjection(bundle: Bundle?, vararg params: Any) {
        super.performInjection(bundle, *params, eventsDispatcherOnMain<EventsDispatcher<Listener>>())
    }

    override fun onAlert(alert: Alert) {
        with(alert) {
            showAlertDialog(
                title?.getString(this@EventsActivity),
                message?.getString(this@EventsActivity),
                positiveButtonText?.getString(this@EventsActivity),
                negativeButtonText?.getString(this@EventsActivity),
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
                    msg.getString(this@EventsActivity),
                    null,
                    it.getString(this@EventsActivity),
                    onClick
                )
            } ?: run {
                showErrorMsg(msg.getString(this@EventsActivity))
            }
        }
    }

    private fun showMsg(textMessage: TextMessage) {
        with(textMessage) {
            actionMsg?.let {
                showMsg(
                    msg.getString(this@EventsActivity),
                    null,
                    it.getString(this@EventsActivity),
                    onClick
                )
            } ?: run {
                showMsg(msg.getString(this@EventsActivity))
            }
        }
    }

    override fun onMessage(message: Message) {

        when (message) {
            is TypedMessage -> {
                when (message) {
                    is TypedMessage.InfoMessage -> {
                        with(message) {
                            showMsg(
                                text.getString(),
                                null,
                                action?.text?.getString(),
                                action?.action
                            )
                        }
                    }

                    is TypedMessage.ErrorMessage -> {
                        with(message) {
                            showErrorMsg(
                                text.getString(),
                                null,
                                action?.text?.getString(),
                                action?.action
                            )
                        }
                    }
                }
            }

            else -> throw IllegalArgumentException("Can not handle passed message type!")
        }
    }
}