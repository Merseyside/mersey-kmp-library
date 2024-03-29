package com.merseyside.merseyLib.archy.android.presentation.activity

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.activity.BaseBindingActivity
import com.merseyside.merseyLib.archy.core.presentation.viewModel.BaseViewModel
import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.utils.reflection.ReflectionUtils
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import kotlin.reflect.KClass

abstract class VMActivity<Binding : ViewDataBinding, Model : BaseViewModel>
    : BaseBindingActivity<Binding>() {

    protected lateinit var viewModel: Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingVariable()
    }

    abstract fun getBindingVariable(): Int

    private fun setBindingVariable() {
        requireBinding().apply {
            setVariable(getBindingVariable(), viewModel)
            executePendingBindings()
        }
    }

    protected abstract fun provideViewModel(
        clazz: KClass<Model>,
        bundle: Bundle?,
        vararg params: Any
    ): Model

    @CallSuper
    override fun performInjection(bundle: Bundle?, vararg params: Any) {
        loadKoinModules(getKoinModules(bundle, *params))
        viewModel = provideViewModel(getViewModelClass(), bundle, *params)
    }

    open fun getKoinModules(bundle: Bundle?, vararg params: Any): List<Module> {
        return emptyList<Module>().also {
            Logger.logInfo("VMFragment", "Empty fragment's koin modules")
        }
    }

    override fun handleError(throwable: Throwable): Boolean {
        return viewModel.onError(throwable)
    }

    override fun updateLanguage(context: Context) {
        //updateLanguage(context)
    }

    protected abstract fun loadingObserver(isLoading: Boolean)

    @Suppress("UNCHECKED_CAST")
    protected fun getViewModelClass(): KClass<Model> {
        return ReflectionUtils.getGenericParameterClass(
            this.javaClass,
            VMActivity::class.java,
            1
        ).kotlin as KClass<Model>
    }
}
