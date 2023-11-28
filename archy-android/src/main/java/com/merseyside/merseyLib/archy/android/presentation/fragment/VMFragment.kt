package com.merseyside.merseyLib.archy.android.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.fragment.BaseBindingFragment
import com.merseyside.merseyLib.archy.core.presentation.viewModel.BaseViewModel
import com.merseyside.utils.reflection.ReflectionUtils
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

    override fun performInjection(bundle: Bundle?, vararg params: Any) {
        viewModel = provideViewModel(getViewModelClass(), *params)
    }

    protected abstract fun provideViewModel(
        clazz: KClass<Model>,
        vararg params: Any
    ): Model

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataBinding(requireBinding())
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