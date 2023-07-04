package com.merseyside.merseyLib.utils.core.koin.savedState.provider.dsl

import com.merseyside.merseyLib.utils.core.koin.savedState.provider.scope.getSavedState
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.dsl.new
import org.koin.dsl.ScopeDSL

inline fun <reified R, reified T1> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}

inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17> ScopeDSL.stateScopedOf(
    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) -> R,
): KoinDefinition<R> = scoped {
    declare(getSavedState<R>())
    new(constructor)
}