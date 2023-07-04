package com.merseyside.merseyLib.utils.core.koin.scope.fragment

import androidx.fragment.app.Fragment
import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.merseyLib.utils.core.koin.scope.delegate.LifecycleScopeDelegate
import com.merseyside.merseyLib.utils.core.koin.scope.delegate.OnBackPressedScopeDelegate
import com.merseyside.merseyLib.utils.core.koin.scope.delegate.ScopeDelegate
import org.koin.android.ext.android.getKoin
import org.koin.core.Koin
import org.koin.core.component.getScopeId
import org.koin.core.component.getScopeName
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.core.scope.ScopeCallback
import org.koin.core.scope.ScopeID
import org.koin.dsl.module

fun Fragment.fragmentScopeLazy(
    scopeIdSuffix: SuffixIdProvider,
    qualifier: Qualifier = getScopeName(),
    strategy: CloseScopeStrategy = CloseScopeStrategy.LIFECYCLE_ON_DESTROY,
    loadKoinModules: () -> List<Module> = { emptyList() }
): ScopeDelegate {
    return fragmentScope(getScopeId(), qualifier, scopeIdSuffix, strategy, loadKoinModules)
}

fun Fragment.fragmentScope(
    scopeID: ScopeID = getScopeId(),
    qualifier: Qualifier = getScopeName(),
    scopeIdSuffix: SuffixIdProvider = { "" },
    strategy: CloseScopeStrategy = CloseScopeStrategy.LIFECYCLE_ON_DESTROY,
    loadKoinModules: () -> List<Module> = { emptyList() }
): ScopeDelegate {
    return when (strategy) {
        CloseScopeStrategy.LIFECYCLE_ON_DESTROY ->
            createLifecycleScopeDelegate(scopeID, qualifier, scopeIdSuffix, loadKoinModules)

        CloseScopeStrategy.ON_BACK_PRESSED ->
            createOnBackPressedScopeDelegate(scopeID, qualifier, scopeIdSuffix, loadKoinModules)
    }
}

private fun Fragment.createLifecycleScopeDelegate(
    scopeID: ScopeID,
    qualifier: Qualifier,
    scopeIdSuffix: SuffixIdProvider,
    loadKoinModules: () -> List<Module>
): LifecycleScopeDelegate {
    return LifecycleScopeDelegate(
        koin = getKoin(),
        lifecycleOwner = this,
        provideScope = provideScope { getScopeIdWithSuffix(scopeID, scopeIdSuffix) },
        createScope = createScope(qualifier, loadKoinModules) { getScopeIdWithSuffix(scopeID, scopeIdSuffix) }
    )
}

private fun Fragment.createOnBackPressedScopeDelegate(
    scopeID: ScopeID,
    qualifier: Qualifier,
    scopeIdSuffix: SuffixIdProvider,
    loadKoinModules: () -> List<Module>
): OnBackPressedScopeDelegate {
    return OnBackPressedScopeDelegate(
        koin = getKoin(),
        fragment = this,
        provideScope = provideScope { getScopeIdWithSuffix(scopeID, scopeIdSuffix) },
        createScope = createScope(qualifier, loadKoinModules) { getScopeIdWithSuffix(scopeID, scopeIdSuffix) }
    )
}

private fun provideScope(scopeID: () -> ScopeID): (Koin) -> Scope? = { koin ->
    koin.getScopeOrNull(scopeID())
}

private fun Fragment.createScope(
    qualifier: Qualifier,
    loadKoinModules: () -> List<Module>,
    scopeID: () -> ScopeID
): (Koin) -> Scope = { koin ->
        val modules = loadKoinModules()
        val module = if (modules.isNotEmpty()) {
            module { koin.loadModules(modules) }
        } else null

        koin.createScope(scopeID(), qualifier, this).also { scope ->
            if (module != null) {
                scope.registerCallback(object : ScopeCallback {
                    override fun onScopeClose(scope: Scope) {
                        koin.unloadModules(listOf(module))
                        Logger.log("Koin", "$scope Modules unloaded")
                    }
                })
            }
        }
    }

private fun getScopeIdWithSuffix(scopeID: ScopeID, suffixLazy: SuffixIdProvider): ScopeID {
    val suffix = suffixLazy().toString()
    return if (suffix.isNotBlank()) "$scopeID@$suffix"
    else scopeID
}

enum class CloseScopeStrategy {
    LIFECYCLE_ON_DESTROY, ON_BACK_PRESSED
}

internal typealias SuffixIdProvider = () -> Any