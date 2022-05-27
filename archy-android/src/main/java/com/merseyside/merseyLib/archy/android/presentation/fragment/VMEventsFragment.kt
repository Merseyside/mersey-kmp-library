package com.merseyside.merseyLib.archy.android.presentation.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.merseyLib.archy.core.presentation.viewModel.EventsViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

abstract class VMEventsFragment<Binding : ViewDataBinding, Model, Listener> :
    NewVMFragment<Binding, Model>(), EventsViewModel.BaseEventsListener
        where Model : EventsViewModel,
              Listener : EventsViewModel.BaseEventsListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        (viewModel as EventsDispatcherOwner<Listener>).eventsDispatcher.bind(
            this,
            this as Listener
        )
    }

    override fun provideViewModel(bundle: Bundle?, vararg params: Any): Model {
        return getViewModel(
            clazz = getViewModelClass(),
            parameters = {
                parametersOf(
                    *params,
                    bundle,
                    eventsDispatcherOnMain<EventsDispatcher<Listener>>()
                )
            }
        )
    }

    override fun onError() {
        showMsg("Error")
    }
}