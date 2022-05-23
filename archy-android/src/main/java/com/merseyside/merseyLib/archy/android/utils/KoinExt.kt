package com.merseyside.merseyLib.archy.android.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

fun <VM : ViewModel> Fragment.navGraphViewModel(
    @IdRes navGraphId: Int,
    clazz: KClass<VM>
): VM {
    TODO()
//    val backStackEntry: NavBackStackEntry by lazy { findNavController().getBackStackEntry(navGraphId) }
//    return getKoinScope(this).getViewModel(
//        owner = { ViewModelOwner(backStackEntry.viewModelStore) },
//        clazz = clazz
//    )
}

@OptIn(KoinInternalApi::class)
fun getKoinScope(any: Any): Scope {
    return when (any) {
        is KoinComponent -> any.getKoin().scopeRegistry.rootScope
        else -> GlobalContext.get().scopeRegistry.rootScope
    }
}