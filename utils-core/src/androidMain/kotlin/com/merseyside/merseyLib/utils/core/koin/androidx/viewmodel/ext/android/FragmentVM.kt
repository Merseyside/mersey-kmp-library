package com.merseyside.merseyLib.utils.core.koin.androidx.viewmodel.ext.android

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import com.merseyside.merseyLib.utils.core.koin.savedState.androidx.viewmodel.ext.addSavedStateIfNeed
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.koin.android.ext.android.getKoinScope
import org.koin.androidx.viewmodel.resolveViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

/**
 * ViewModel API from Fragment
 *
 * @author Arnaud Giuliani
 */

/**
 * Retrieve Lazy ViewModel instance for Fragment
 * @param qualifier
 * @param ownerProducer
 * @param extrasProducer
 * @param parameters
 */
@MainThread
inline fun <reified T : ViewModel> Fragment.viewModel(
    qualifier: Qualifier? = null,
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        getViewModel(qualifier, ownerProducer, extrasProducer, parameters)
    }
}

/**
 * Retrieve ViewModel instance for Fragment
 * @param qualifier
 * @param ownerProducer
 * @param extrasProducer
 * @param parameters
 */
@OptIn(KoinInternalApi::class)
@MainThread
inline fun <reified T : ViewModel> Fragment.getViewModel(
    qualifier: Qualifier? = null,
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    return getViewModel(
        clazz = T::class,
        qualifier = qualifier,
        ownerProducer = ownerProducer,
        extrasProducer = extrasProducer,
        parameters = parameters,
        scope = getKoinScope()
    )
}

@OptIn(KoinInternalApi::class)
@MainThread
fun <T : ViewModel> Fragment.getViewModel(
    clazz: KClass<T>,
    qualifier: Qualifier? = null,
    ownerProducer: () -> ViewModelStoreOwner = { this },
    extrasProducer: (() -> CreationExtras)? = null,
    parameters: ParametersDefinition? = null,
    scope: Scope = getKoinScope()
): T {
    return resolveViewModel(
        clazz,
        ownerProducer().viewModelStore,
        extras = extrasProducer?.invoke() ?: this.defaultViewModelCreationExtras,
        qualifier = qualifier,
        parameters = addSavedStateIfNeed(clazz, parameters),
        scope = scope
    )
}

