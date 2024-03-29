package com.merseyside.sample.view.fragment.view

import android.content.Context
import com.merseyside.merseyLib.archy.android.presentation.fragment.EventsFragment
import com.merseyside.merseyLib.utils.core.koin.androidx.viewmodel.ext.android.getViewModel
import com.merseyside.merseyLib.utils.core.koin.savedState.scope.declareStateProvider
import com.merseyside.merseyLib.utils.core.koin.androidx.scope.fragmentScope
import com.merseyside.sample.BR
import com.merseyside.sample.R
import com.merseyside.sample.databinding.FragmentKoinStateBinding
import com.merseyside.sample.view.fragment.di.koinStateModule
import com.merseyside.sample.viewModel.KoinStateViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

class KoinStateFragment : EventsFragment<FragmentKoinStateBinding, KoinStateViewModel,
        KoinStateViewModel.KoinStateEventsListener>(), AndroidScopeComponent,
    KoinStateViewModel.KoinStateEventsListener {

    override val scope: Scope by fragmentScope { listOf(koinStateModule) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scope.declareStateProvider()
    }

    override fun onNavigateUp() {}

    override fun getLayoutId() = R.layout.fragment_koin_state
    override fun getTitle(context: Context) = null
    override fun isNavigateUpEnabled(): Boolean {
        return true
    }

    override fun getBindingVariable() = BR.viewModel
    override fun provideViewModel(
        clazz: KClass<KoinStateViewModel>,
        vararg params: Any
    ): KoinStateViewModel {
        return getViewModel(
            clazz = clazz,
            parameters = { parametersOf(*params) }
        )
    }
}