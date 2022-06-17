package com.merseyside.sample.viewModel

import com.merseyside.merseyLib.archy.core.presentation.viewModel.EventsViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

class TestViewModel(override val eventsDispatcher: EventsDispatcher<TestEventsListener>)
    : EventsViewModel() {

    interface TestEventsListener : BaseEventsListener {
        fun doSomeJob()
    }
}