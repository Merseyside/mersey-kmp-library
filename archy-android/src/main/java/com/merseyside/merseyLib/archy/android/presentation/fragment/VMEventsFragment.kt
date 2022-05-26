package com.merseyside.merseyLib.archy.android.presentation.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.merseyLib.archy.core.presentation.model.BaseViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

abstract class VMEventsFragment<Binding : ViewDataBinding, Model, Listener : Any>
    : VMFragment<Binding, Model>() where Model : BaseViewModel {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        (viewModel as EventsDispatcherOwner<Listener>).eventsDispatcher.bind(this, this as Listener)
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
}