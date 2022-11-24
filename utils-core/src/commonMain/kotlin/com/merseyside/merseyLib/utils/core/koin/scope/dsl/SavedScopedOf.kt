package com.merseyside.merseyLib.utils.core.koin.scope.dsl

import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.merseyLib.utils.core.state.StateSaver
import org.koin.core.module.KoinDefinition
import org.koin.dsl.ScopeDSL
import com.merseyside.merseyLib.utils.core.koin.scope.newSavedState as new

inline fun <reified R> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13> ScopeDSL.savedScopedOf(
    crossinline constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> R,
): KoinDefinition<R> where R : StateSaver = scoped { new(constructor) }