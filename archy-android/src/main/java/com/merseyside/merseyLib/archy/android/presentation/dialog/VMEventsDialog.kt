package com.merseyside.merseyLib.archy.android.presentation.dialog

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.merseyLib.archy.android.presentation.fragment.VMFragment
import com.merseyside.merseyLib.archy.core.presentation.viewModel.BaseViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner

abstract class VMEventsDialog<B : ViewDataBinding, M, Listener : Any>
    : VMFragment<B, M>() where M : BaseViewModel {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        (viewModel as EventsDispatcherOwner<Listener>).eventsDispatcher.bind(this, this as Listener)
    }
}