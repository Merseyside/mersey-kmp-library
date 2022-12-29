package com.merseyside.merseyLib.utils.core.koin.module.dsl

import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.merseyLib.utils.core.state.StateSaver
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.dsl.ScopeDSL
import com.merseyside.merseyLib.utils.core.koin.scope.newSavedState as new

inline fun <reified R> Module.viewModelOf(
    crossinline constructor: (SavedState) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1> Module.viewModelOf(
    crossinline constructor: (SavedState, T1) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17> Module.viewModelOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) -> R,
): KoinDefinition<R> where R : ViewModel, R : StateSaver = viewModel { new(constructor) }

