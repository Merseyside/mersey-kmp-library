package com.merseyside.kmpMerseyLib.arch.presentation.activity

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.kmpMerseyLib.arch.presentation.di.BaseViewModel
import com.merseyside.kmpMerseyLib.arch.presentation.di.SavedStateViewModel
import com.merseyside.archy.presentation.activity.BaseBindingActivity
import com.merseyside.kmpMerseyLib.arch.presentation.di.SavedStateViewModel.Companion.INSTANCE_STATE_KEY
import com.merseyside.kmpMerseyLib.arch.presentation.fragment.BaseVMFragment
import com.merseyside.kmpMerseyLib.utils.SavedState
import com.merseyside.utils.PermissionManager
import com.merseyside.utils.reflection.ReflectionUtils
import com.merseyside.utils.serialization.putSerialize
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.getViewModel
import kotlin.reflect.KClass

abstract class BaseVMActivity<B : ViewDataBinding, M : BaseViewModel> : BaseBindingActivity<B>() {

    protected lateinit var viewModel: M

    private val messageObserver = { message: BaseViewModel.TextMessage? ->
        if (message != null) {
            if (message.isError) {
                showErrorMsg(message)
            } else {
                showMsg(message)
            }

            viewModel.messageLiveEvent.value = null
        }
    }

    private val loadingObserver = { isLoading: Boolean -> this.loadingObserver(isLoading) }
    private val alertDialogModel = { model: BaseViewModel.AlertDialogModel? ->
        model?.apply {
            showAlertDialog(title, message, positiveButtonText, negativeButtonText, onPositiveClick, onNegativeClick, isCancelable)

            viewModel.alertDialogLiveEvent.value = null
        }

        Unit
    }

    private val permissionObserver = { pair: Pair<Array<String>, Int>? ->
        if (pair != null) {
            PermissionManager.requestPermissions(this, *pair.first, requestCode = pair.second)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingVariable()

        //viewModel.updateLanguage(this)

        observeViewModel()
    }

    override fun performInjection(bundle: Bundle?) {
        viewModel = lifecycleScope.getViewModel(owner = this, clazz = persistentClass)
    }

    abstract fun getBindingVariable(): Int

    private fun setBindingVariable() {
        binding.apply {
            setVariable(getBindingVariable(), viewModel)
            executePendingBindings()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (viewModel is SavedStateViewModel) {
            val savedState = SavedState()

            (viewModel as SavedStateViewModel).onSaveState(savedState)
            outState.putSerialize(INSTANCE_STATE_KEY, savedState.getAll(), MapSerializer(String.serializer(), String.serializer()))
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            messageLiveEvent.addObserver(messageObserver)
            isInProgressLiveData.addObserver(loadingObserver)
            alertDialogLiveEvent.addObserver(alertDialogModel)
            grantPermissionLiveEvent.addObserver(permissionObserver)
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

    internal val persistentClass: KClass<M> =
        ReflectionUtils.getGenericParameterClass(this.javaClass, BaseVMFragment::class.java, 1).kotlin as KClass<M>
}