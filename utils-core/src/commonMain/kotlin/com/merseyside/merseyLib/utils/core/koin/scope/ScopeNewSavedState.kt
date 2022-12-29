package com.merseyside.merseyLib.utils.core.koin.scope

import com.merseyside.merseyLib.utils.core.koin.ext.getKoinStateHolder
import com.merseyside.merseyLib.utils.core.koin.ext.getSavedStateFromStateHolder
import com.merseyside.merseyLib.utils.core.state.SavedState
import com.merseyside.merseyLib.utils.core.state.StateSaver
import org.koin.core.scope.Scope

inline fun <reified R : StateSaver> Scope.newSavedState(
    constructor: (SavedState) -> R,
): R = constructor(getSavedStateFromStateHolder<R>()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1> Scope.newSavedState(
    constructor: (SavedState, T1) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2> Scope.newSavedState(
    constructor: (SavedState, T1, T2) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}

inline fun <reified R : StateSaver, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7, reified T8, reified T9, reified T10, reified T11, reified T12, reified T13, reified T14, reified T15, reified T16, reified T17> Scope.newSavedState(
    constructor: (SavedState, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17) -> R,
): R = constructor(getSavedStateFromStateHolder<R>(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()).also {
    getKoinStateHolder()?.addStateSaver(it)
}