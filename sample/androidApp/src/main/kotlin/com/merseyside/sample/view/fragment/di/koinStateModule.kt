package com.merseyside.sample.view.fragment.di

import com.merseyside.merseyLib.utils.core.koin.scope.dsl.viewmodel.viewModelOf
import com.merseyside.sample.view.fragment.view.KoinStateFragment
import org.koin.dsl.module
import com.merseyside.sample.viewModel.KoinStateViewModel
import com.merseyside.sample.manager.SomeManager

val koinStateModule = module {

    scope<KoinStateFragment> {
        viewModelOf(::KoinStateViewModel)
    }

}