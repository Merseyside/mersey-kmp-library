package com.merseyside.merseyLib.archy.android.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.dialog.BaseBindingDialog
import com.merseyside.merseyLib.archy.core.presentation.viewModel.BaseViewModel
import com.merseyside.utils.reflection.ReflectionUtils
import kotlin.reflect.KClass

abstract class VMDialog<B : ViewDataBinding, M : BaseViewModel> : BaseBindingDialog<B>() {

    protected lateinit var viewModel: M

    private val errorObserver = { throwable: Throwable? ->
        throwable?.let {
            this.handleError(it)
        }
        Unit
    }

    private val messageObserver = { message: BaseViewModel.TextMessage? ->
        message?.let {
            if (it.isError) {
                showErrorMsg(it)
            } else {
                showMsg(it)
            }
        }
        Unit
    }

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.apply {
            errorLiveEvent.ld().observe(this@VMDialog, errorObserver)
            messageLiveEvent.ld().observe(this@VMDialog, messageObserver)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun showMsg(textMessage: BaseViewModel.TextMessage) {
        if (textMessage.actionMsg.isNullOrEmpty()) {
            showMsg(textMessage.msg)
        } else {
            showMsg(textMessage.msg, null, textMessage.actionMsg, textMessage.onClick)
        }
    }

    private fun showErrorMsg(textMessage: BaseViewModel.TextMessage) {
        if (textMessage.actionMsg.isNullOrEmpty()) {
            showErrorMsg(textMessage.msg)
        } else {
            showErrorMsg(textMessage.msg, null, textMessage.actionMsg, textMessage.onClick)
        }
    }

    protected open fun getPersistentClass(): KClass<M> {
        return ReflectionUtils.getGenericParameterClass(
            this.javaClass,
            VMDialog::class.java,
            1
        ).kotlin as KClass<M>
    }

}