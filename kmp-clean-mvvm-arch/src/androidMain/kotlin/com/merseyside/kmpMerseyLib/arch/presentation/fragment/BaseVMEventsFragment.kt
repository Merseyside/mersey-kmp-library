package com.merseyside.kmpMerseyLib.arch.presentation.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.kmpMerseyLib.arch.presentation.di.BaseViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner

abstract class BaseVMEventsFragment<B : ViewDataBinding, M, Listener : Any>
    : BaseVMFragment<B, M>() where M : BaseViewModel {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        (viewModel as EventsDispatcherOwner<Listener>).eventsDispatcher.bind(this, this as Listener)
    }
}