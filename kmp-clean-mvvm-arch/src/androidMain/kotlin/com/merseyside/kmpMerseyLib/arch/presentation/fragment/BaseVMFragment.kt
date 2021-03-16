package com.merseyside.kmpMerseyLib.arch.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.fragment.BaseBindingFragment
import com.merseyside.kmpMerseyLib.arch.presentation.di.BaseViewModel
import com.merseyside.kmpMerseyLib.arch.presentation.di.SavedStateViewModel
import com.merseyside.kmpMerseyLib.arch.presentation.di.SavedStateViewModel.Companion.INSTANCE_STATE_KEY
import com.merseyside.kmpMerseyLib.utils.SavedState
import com.merseyside.utils.PermissionManager
import com.merseyside.utils.ext.logMsg
import com.merseyside.utils.reflection.ReflectionUtils
import com.merseyside.utils.serialization.putSerialize
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class BaseVMFragment<B : ViewDataBinding, M : BaseViewModel> : BaseBindingFragment<B>() {

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

    private val errorObserver = { throwable: Throwable? ->
        if (throwable != null) {
            this.handleError(throwable)
        }
    }

    private val progressObserver = { isLoading: Boolean ->
        this.loadingObserver(isLoading)
    }

    private val alertDialogModelObserver = { model: BaseViewModel.AlertDialogModel? ->
        model?.apply {
            showAlertDialog(title, message, positiveButtonText, negativeButtonText, onPositiveClick, onNegativeClick, isSingleAction, isCancelable)
            viewModel.alertDialogLiveEvent.value = null
        }
        Unit
    }

    private val permissionObserver = { pair: Pair<Array<String>, Int>? ->
        if (pair != null) {
            PermissionManager.requestPermissions(this, *pair.first, requestCode = pair.second)
        }
    }

    abstract fun getBindingVariable(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun performInjection(bundle: Bundle?) {
        viewModel = lifecycleScope.getViewModel(owner = this, clazz = persistentClass) { parametersOf(bundle) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getBinding().apply {
            setVariable(getBindingVariable(), viewModel)
            executePendingBindings()
        }

        viewModel.apply {
            errorLiveEvent.addObserver(errorObserver)
            messageLiveEvent.addObserver(messageObserver)
            isInProgressLiveData.addObserver(progressObserver)
            alertDialogLiveEvent.addObserver(alertDialogModelObserver)
            grantPermissionLiveEvent.addObserver(permissionObserver)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (viewModel is SavedStateViewModel) {
            val bundle = SavedState().also { logMsg("here") }

            (viewModel as SavedStateViewModel).onSaveState(bundle)
            outState.putSerialize(INSTANCE_STATE_KEY, bundle.getAll(), MapSerializer(String.serializer(), String.serializer()))
        }
    }

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)
        //viewModel.updateLanguage(context)
    }

    protected open fun loadingObserver(isLoading: Boolean) {}

    private fun showErrorMsg(textMessage: BaseViewModel.TextMessage) {
        if (textMessage.actionMsg.isNullOrEmpty()) {
            showErrorMsg(textMessage.msg)
        } else {
            showErrorMsg(textMessage.msg, null, textMessage.actionMsg, textMessage.onClick)
        }
    }

    private fun showMsg(textMessage: BaseViewModel.TextMessage) {
        if (textMessage.actionMsg.isNullOrEmpty()) {
            showMsg(textMessage.msg)
        } else {
            showMsg(textMessage.msg, null, textMessage.actionMsg, textMessage.onClick)
        }
    }

    protected fun showProgress() {
        viewModel.showProgress()
    }

    protected fun hideProgress() {
        viewModel.hideProgress()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.apply {
            errorLiveEvent.removeObserver(errorObserver)
            messageLiveEvent.removeObserver(messageObserver)
            isInProgressLiveData.removeObserver(progressObserver)
            alertDialogLiveEvent.removeObserver(alertDialogModelObserver)
            grantPermissionLiveEvent.removeObserver(permissionObserver)
        }
    }

    internal val persistentClass: KClass<M> =
        ReflectionUtils.getGenericParameterClass(this.javaClass, BaseVMFragment::class.java, 1).kotlin as KClass<M>
}
