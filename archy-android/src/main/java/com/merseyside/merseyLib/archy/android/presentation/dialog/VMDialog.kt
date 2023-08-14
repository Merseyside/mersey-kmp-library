package com.merseyside.merseyLib.archy.android.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.dialog.BindingDialog
import com.merseyside.merseyLib.archy.android.presentation.extensions.getString
import com.merseyside.merseyLib.archy.core.presentation.viewModel.BaseViewModel
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.TextMessage
import com.merseyside.utils.reflection.ReflectionUtils
import kotlin.reflect.KClass

abstract class VMDialog<Binding : ViewDataBinding, Model : BaseViewModel> :
    BindingDialog<Binding>() {

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
        viewModel = provideViewModel(getViewModelClass(), *params)
    }

    protected abstract fun provideViewModel(clazz: KClass<Model>, vararg params: Any): Model

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
    private fun getViewModelClass(): KClass<Model> {
        return ReflectionUtils.getGenericParameterClass(
            this.javaClass,
            VMDialog::class.java,
            1
        ).kotlin as KClass<Model>
    }

}