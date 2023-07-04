package com.merseyside.merseyLib.utils.core.koin.savedState.androidx.viewmodel.ext

import com.merseyside.merseyLib.utils.core.koin.savedState.ext.addSavedState
import androidx.fragment.app.Fragment
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.utils.core.koin.savedState.key.getStateKey
import com.merseyside.merseyLib.utils.core.koin.savedState.provider.androidx.getSavedState
import com.merseyside.merseyLib.utils.core.koin.savedState.provider.androidx.registerSavedState
import com.merseyside.merseyLib.utils.core.savedState.SavedState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

fun <T : ViewModel> Fragment.addSavedStateIfNeed(clazz: KClass<T>, parametersDefinition: ParametersDefinition?): ParametersDefinition? {
    return if (clazz.java.needSavedState()) {
        val savedState = getSavedState(savedStateRegistry, getStateKey(clazz))
        registerSavedState(savedStateRegistry, savedState, getStateKey(clazz))

        "here".log()
        (parametersDefinition?.addSavedState(savedState) ?: { parametersOf(savedState) }).also { it.invoke().values.log("here1") }

    } else parametersDefinition
}

@PublishedApi
internal fun <T : ViewModel> Class<T>.needSavedState(): Boolean {
    return constructors[0].parameterTypes.any { p -> p == SavedState::class.java }
}