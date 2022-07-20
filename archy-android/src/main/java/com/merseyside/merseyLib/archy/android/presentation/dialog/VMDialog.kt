package com.merseyside.merseyLib.archy.android.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.dialog.BaseBindingDialog
import com.merseyside.merseyLib.archy.android.presentation.extensions.getString
import com.merseyside.merseyLib.archy.core.presentation.viewModel.BaseViewModel
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.TextMessage
import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.utils.reflection.ReflectionUtils
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class VMDialog<Binding : ViewDataBinding, Model : BaseViewModel>
    : BaseBindingDialog<Binding>() {

    protected lateinit var viewModel: Model

    abstract fun getBindingVariable(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        performInjection(savedInstanceState)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        requireBinding().apply {
            setVariable(getBindingVariable(), viewModel)
            executePendingBindings()
        }
        return dialog
    }

    override fun performInjection(bundle: Bundle?, vararg params: Any) {
        loadKoinModules(getKoinModules(bundle, *params))
        viewModel = provideViewModel(bundle, params)
    }

    open fun getKoinModules(bundle: Bundle?, vararg params: Any): List<Module> {
        return emptyList<Module>().also {
            Logger.logInfo("VMFragment", "Empty fragment's koin modules")
        }
    }

    protected open fun provideViewModel(bundle: Bundle?, vararg params: Any): Model {
        return getViewModel(
            clazz = getViewModelClass(),
            parameters = { parametersOf(bundle, *params) }
        )
    }

    private fun showErrorMsg(textMessage: TextMessage) {
        with(textMessage) {
            actionMsg?.let {
                showErrorMsg(
                    msg.getString(this@VMDialog.requireContext()),
                    null,
                    it.getString(this@VMDialog.requireContext()),
                    onClick
                )
            } ?: run {
                showErrorMsg(msg.getString(this@VMDialog.requireContext()))
            }
        }
    }

    private fun showMsg(textMessage: TextMessage) {
        with(textMessage) {
            actionMsg?.let {
                showMsg(
                    msg.getString(this@VMDialog.requireContext()),
                    null,
                    it.getString(this@VMDialog.requireContext()),
                    onClick
                )
            } ?: run {
                showMsg(msg.getString(this@VMDialog.requireContext()))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun getViewModelClass(): KClass<Model> {
        return ReflectionUtils.getGenericParameterClass(
            this.javaClass,
            VMDialog::class.java,
            1
        ).kotlin as KClass<Model>
    }

}