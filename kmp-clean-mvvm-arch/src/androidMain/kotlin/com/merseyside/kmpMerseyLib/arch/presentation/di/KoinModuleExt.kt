package com.merseyside.kmpMerseyLib.arch.presentation.di

import android.os.Bundle
import com.merseyside.kmpMerseyLib.arch.presentation.di.SavedStateViewModel.Companion.INSTANCE_STATE_KEY
import com.merseyside.kmpMerseyLib.utils.SavedState
import com.merseyside.utils.ext.isNotNullAndEmpty
import com.merseyside.utils.serialization.getSerialize
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.koin.androidx.viewmodel.dsl.setIsViewModel
import org.koin.core.definition.BeanDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import java.lang.IllegalArgumentException

inline fun <reified T : SavedStateViewModel> Module.savedStateViewModel(
    qualifier: Qualifier? = null,
    override: Boolean = false,
    noinline viewModelDefinition: Scope.() -> T
): BeanDefinition<T> {

    val beanDefinition = factory(qualifier, override) { (bundle: Bundle) ->
        viewModelDefinition().apply {
            if (bundle.isNotNullAndEmpty()) {
                val savedState = SavedState().apply {
                    addAll(bundle.getSerialize(
                        INSTANCE_STATE_KEY,
                        MapSerializer(String.serializer(), String.serializer())
                    ) ?: throw IllegalArgumentException()
                    )
                }

                onRestoreState(savedState)
            }
        }
    }
    beanDefinition.setIsViewModel()
    return beanDefinition
}