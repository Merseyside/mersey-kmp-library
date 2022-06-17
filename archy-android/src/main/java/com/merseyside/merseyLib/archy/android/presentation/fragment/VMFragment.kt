package com.merseyside.merseyLib.archy.android.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.fragment.BaseBindingFragment
import com.merseyside.merseyLib.archy.core.di.state.getStateKey
import com.merseyside.merseyLib.archy.core.di.state.saveState
import com.merseyside.merseyLib.archy.core.presentation.viewModel.BaseViewModel
import com.merseyside.merseyLib.kotlin.Logger
import com.merseyside.merseyLib.utils.core.state.StateSaver
import com.merseyside.utils.reflection.ReflectionUtils
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class VMFragment<Binding : ViewDataBinding, Model : BaseViewModel>
    : BaseBindingFragment<Binding>() {

    protected lateinit var viewModel: Model

    abstract fun getBindingVariable(): Int

    open fun initDataBinding(binding: Binding) {
        binding.apply {
            setVariable(getBindingVariable(), viewModel)
            executePendingBindings()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun performInjection(bundle: Bundle?, vararg params: Any) {
        loadKoinModules(getKoinModules())
        viewModel = provideViewModel(bundle, params)
    }

    open fun getKoinModules(): List<Module> {
        return emptyList<Module>().also { Logger.logInfo("VMFragment", "Empty fragment's koin modules") }
    }

    protected open fun provideViewModel(bundle: Bundle?, vararg params: Any): Model {
        return getViewModel(
            clazz = getViewModelClass(),
            parameters = { parametersOf(*params, bundle) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDataBinding(requireBinding())
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (viewModel as? StateSaver)?.saveState(outState, getStateKey(getViewModelClass()))
    }

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)
        //viewModel.updateLanguage(context)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun getViewModelClass(): KClass<Model> {
        return ReflectionUtils.getGenericParameterClass(
            this.javaClass,
            VMFragment::class.java,
            1
        ).kotlin as KClass<Model>
    }
}