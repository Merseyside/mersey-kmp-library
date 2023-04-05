package com.merseyside.sample.view.fragment.view

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import com.merseyside.merseyLib.archy.android.presentation.fragment.VMEventsFragment
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.utils.core.koin.androidx.viewmodel.ext.android.getViewModel
import com.merseyside.merseyLib.utils.core.koin.savedState.scope.declareStateProvider
import com.merseyside.sample.BR
import com.merseyside.sample.R
import com.merseyside.sample.databinding.FragmentKoinStateBinding
import com.merseyside.sample.view.fragment.di.koinStateModule
import com.merseyside.sample.viewModel.KoinStateViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

class KoinStateFragment : VMEventsFragment<FragmentKoinStateBinding, KoinStateViewModel,
        KoinStateViewModel.KoinStateEventsListener>(), AndroidScopeComponent,
    KoinStateViewModel.KoinStateEventsListener {

    override val scope: Scope by fragmentScope()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scope.declareStateProvider()
    }

    override fun getLayoutId() = R.layout.fragment_koin_state
    override fun getTitle(context: Context) = null
    override fun getBindingVariable() = BR.viewModel

    override fun getKoinModules(bundle: Bundle?, vararg params: Any): List<Module> {
        SavedStateHandle
        return listOf(koinStateModule)
    }

    override fun provideViewModel(
        clazz: KClass<KoinStateViewModel>,
        bundle: Bundle?,
        vararg params: Any
    ): KoinStateViewModel {
        return getViewModel(
            clazz = clazz,
            parameters = { parametersOf(*params) }
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.log("kek")
    }


}