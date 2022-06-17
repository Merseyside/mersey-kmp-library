package com.merseyside.merseyLib.archy.android.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.dialog.BaseBindingDialog
import com.merseyside.merseyLib.archy.core.presentation.viewModel.BaseViewModel
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.TextMessage
import com.merseyside.merseyLib.kotlin.Logger
import com.merseyside.merseyLib.kotlin.extensions.isNotNullAndEmpty
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

    override fun onCreate(onSavedInstanceState: Bundle?) {
        performInjection(onSavedInstanceState)
        super.onCreate(onSavedInstanceState)
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
        loadKoinModules(getKoinModules())
        viewModel = provideViewModel(bundle, params)
    }

    open fun getKoinModules(): List<Module> {
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
            if (actionMsg.isNotNullAndEmpty()) {
                showErrorMsg(textMessage.msg, null, actionMsg, textMessage.onClick)
            } else {
                showErrorMsg(textMessage.msg)
            }
        }
    }

    private fun showMsg(textMessage: TextMessage) {
        with(textMessage) {
            if (actionMsg.isNotNullAndEmpty()) {
                showMsg(textMessage.msg, null, actionMsg, textMessage.onClick)
            } else {
                showMsg(textMessage.msg)
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