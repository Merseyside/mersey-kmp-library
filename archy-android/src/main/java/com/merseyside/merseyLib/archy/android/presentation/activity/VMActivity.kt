package com.merseyside.merseyLib.archy.android.presentation.activity

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.activity.BaseBindingActivity
import com.merseyside.merseyLib.archy.core.di.state.getStateKey
import com.merseyside.merseyLib.archy.core.presentation.model.BaseViewModel
import com.merseyside.merseyLib.archy.core.di.state.saveState
import com.merseyside.merseyLib.utils.core.state.StateSaver
import com.merseyside.utils.reflection.ReflectionUtils
import com.merseyside.utils.requestPermissions
import kotlin.reflect.KClass

abstract class VMActivity<Binding : ViewDataBinding, Model : BaseViewModel>
    : BaseBindingActivity<Binding>() {

    protected abstract val viewModel: Model

    private val messageObserver = { message: BaseViewModel.TextMessage? ->
        if (message != null) {
            if (message.isError) {
                showErrorMsg(message)
            } else {
                showMsg(message)
            }
        }
    }

    private val loadingObserver = { isLoading: Boolean -> this.loadingObserver(isLoading) }
    private val alertDialogModel = { model: BaseViewModel.AlertDialogModel? ->
        model?.apply {
            showAlertDialog(title, message, positiveButtonText, negativeButtonText, onPositiveClick, onNegativeClick, isCancelable)
        }

        Unit
    }

    private val permissionObserver = { pair: Pair<Array<String>, Int>? ->
        if (pair != null) {
            requestPermissions(*pair.first, requestCode = pair.second)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingVariable()

        //viewModel.updateLanguage(this)

        observeViewModel()
    }

    abstract fun getBindingVariable(): Int

    private fun setBindingVariable() {
        requireBinding().apply {
            setVariable(getBindingVariable(), viewModel)
            executePendingBindings()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (viewModel as? StateSaver)?.saveState(outState, getStateKey(getViewModelClass()))
    }

    private fun observeViewModel() {
        viewModel.apply {
            messageLiveEvent.ld().observe(this@VMActivity, messageObserver)
            isInProgress.ld().observe(this@VMActivity, loadingObserver)
            alertDialogLiveEvent.ld().observe(this@VMActivity, alertDialogModel)
            grantPermissionLiveEvent.ld().observe(this@VMActivity, permissionObserver)
        }
    }

    override fun handleError(throwable: Throwable) {
        viewModel.onError(throwable)
    }

    override fun updateLanguage(context: Context) {
        //viewModel.updateLanguage(context)
    }

    protected abstract fun loadingObserver(isLoading: Boolean)

    private fun showErrorMsg(textMessage: BaseViewModel.TextMessage) {
        if (textMessage.actionMsg.isNullOrEmpty()) {
            showErrorMsg(textMessage.msg)
        } else {
            showErrorMsg(textMessage.msg, null, textMessage.actionMsg!!, textMessage.onClick)
        }
    }

    private fun showMsg(textMessage: BaseViewModel.TextMessage) {
        if (textMessage.actionMsg.isNullOrEmpty()) {
            showMsg(textMessage.msg)
        } else {
            showMsg(textMessage.msg, null, textMessage.actionMsg!!, textMessage.onClick)
        }
    }

    protected fun getViewModelClass(): KClass<Model> {
        return ReflectionUtils.getGenericParameterClass(
            this.javaClass,
            VMActivity::class.java,
            1
        ).kotlin as KClass<Model>
    }
}
