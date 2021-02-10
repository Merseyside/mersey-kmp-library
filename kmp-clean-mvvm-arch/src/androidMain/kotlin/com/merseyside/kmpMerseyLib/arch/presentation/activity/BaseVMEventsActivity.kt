package com.merseyside.kmpMerseyLib.arch.presentation.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.activity.BaseVMActivity
import com.merseyside.archy.presentation.model.BaseViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner

abstract class BaseVMEventsActivity<B: ViewDataBinding, M, Listener : Any>
    : BaseVMActivity<B, M>() where M : BaseViewModel, M: EventsDispatcherOwner<Listener> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        viewModel.eventsDispatcher.bind(this, this as Listener)
    }
}