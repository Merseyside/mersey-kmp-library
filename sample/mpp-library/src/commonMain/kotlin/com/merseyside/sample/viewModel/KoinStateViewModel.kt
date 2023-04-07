package com.merseyside.sample.viewModel

import com.merseyside.merseyLib.archy.core.presentation.viewModel.EventsViewModel
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.utils.core.savedState.SavedState
import com.merseyside.merseyLib.utils.core.savedState.delegate.coroutine.stateFlow.serializableStateFlow
import com.merseyside.merseyLib.utils.core.savedState.delegate.saveable
import com.merseyside.sample.manager.SomeManager
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable

class KoinStateViewModel(
    override val eventsDispatcher: EventsDispatcher<KoinStateEventsListener>,
    private val savedState: SavedState,
) : EventsViewModel(), EventsDispatcherOwner<KoinStateViewModel.KoinStateEventsListener> {

    private val someManager: SomeManager by savedState.saveable { savedState -> SomeManager(savedState) }

    val nameStateFlow: MutableStateFlow<String?> = MutableStateFlow("")
    val ageStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    val dataStateFlow: StateFlow<SomeData> by savedState.serializableStateFlow(viewModelScope) {
        combineState(nameStateFlow, ageStateFlow.mapState { it?.toInt() }) { name, age ->
            SomeData(name ?: "" , age ?: 0)
        }
    }

    val managerData = combine(nameStateFlow, ageStateFlow.mapState { it?.toInt() }) { name, age ->
        "$name $age"
    }

    init {
        dataStateFlow.onEach { it.log("SavedState") }.launchIn(viewModelScope)
        managerData.onEach { someManager.nameWithAge = it }.launchIn(viewModelScope)
    }

    interface KoinStateEventsListener : BaseEventsListener
}

@Serializable
data class SomeData(
    val name: String,
    val age: Int
)