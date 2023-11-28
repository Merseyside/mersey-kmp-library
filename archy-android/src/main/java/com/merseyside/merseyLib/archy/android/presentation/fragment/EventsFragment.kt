package com.merseyside.merseyLib.archy.android.presentation.fragment

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

abstract class EventsFragment<Binding : ViewDataBinding, Model, Listener> :
    VMFragment<Binding, Model>(), EventsViewModel.BaseEventsListener
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
        super.performInjection(
            bundle,
            eventsDispatcherOnMain<EventsDispatcher<Listener>>(),
            *params
        )
    }

    @Deprecated("Use showMsg(Message) method")
    override fun onAlert(alert: Alert) {
        with(alert) {
            showAlertDialog(
                title?.getString(requireContext()),
                message?.getString(requireContext()),
                positiveButtonText?.getString(requireContext()),
                negativeButtonText?.getString(requireContext()),
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

    @Deprecated("Use showMsg(Message) method")
    override fun onMessage(message: TextMessage) {
        if (message.isError) {
            showErrorMsg(message)
        } else {
            showMsg(message)
        }
    }

    override fun onConnectionStateChanged(state: Boolean) {}

    @Deprecated("Use showMsg(Message) method")
    private fun showErrorMsg(textMessage: TextMessage) {
        with(textMessage) {
            actionMsg?.let {
                showErrorMsg(
                    msg.getString(this@EventsFragment.requireContext()),
                    null,
                    it.getString(this@EventsFragment.requireContext()),
                    onClick
                )
            } ?: run {
                showErrorMsg(msg.getString(this@EventsFragment.requireContext()))
            }
        }
    }

    @Deprecated("Use showMsg(Message) method")
    private fun showMsg(textMessage: TextMessage) {
        with(textMessage) {
            actionMsg?.let {
                showMsg(
                    msg.getString(this@EventsFragment.requireContext()),
                    null,
                    it.getString(this@EventsFragment.requireContext()),
                    onClick
                )
            } ?: run {
                showMsg(msg.getString(this@EventsFragment.requireContext()))
            }
        }
    }


    override fun showMsg(message: Message) {

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