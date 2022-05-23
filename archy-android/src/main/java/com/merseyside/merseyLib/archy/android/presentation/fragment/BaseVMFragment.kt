package com.merseyside.merseyLib.archy.android.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import androidx.databinding.ViewStubProxy
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.merseyside.archy.presentation.fragment.BaseBindingFragment
import com.merseyside.merseyLib.archy.core.presentation.model.BaseViewModel
import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel
import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel.Companion.INSTANCE_STATE_KEY
import com.merseyside.merseyLib.utils.core.SavedState
import com.merseyside.utils.ext.getSerialize
import com.merseyside.utils.ext.putSerialize
import com.merseyside.utils.reflection.ReflectionUtils
import com.merseyside.utils.requestPermissions
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlin.reflect.KClass


abstract class BaseVMFragment<Binding : ViewDataBinding, Model : BaseViewModel>
    : BaseBindingFragment<Binding>() {

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

    private val errorObserver = { throwable: Throwable? ->
        if (throwable != null) {
            this.handleError(throwable)
        }
    }

    private val progressObserver = { isLoading: Boolean? ->
        this.loadingObserver(isLoading ?: false)
    }

    private val alertDialogModelObserver = { model: BaseViewModel.AlertDialogModel? ->
        model?.apply {
            showAlertDialog(
                title,
                message,
                positiveButtonText,
                negativeButtonText,
                onPositiveClick,
                onNegativeClick,
                isSingleAction,
                isCancelable
            )
        }
        Unit
    }

    private val permissionObserver = { pair: Pair<Array<String>, Int>? ->
        if (pair != null) {
            requestPermissions(*pair.first, requestCode = pair.second)
        }
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initDataBinding(requireBinding())

        viewModel.apply {
            errorLiveEvent.ld().observe(viewLifecycleOwner, errorObserver)
            messageLiveEvent.ld().observe(viewLifecycleOwner, messageObserver)
            isInProgress.ld().observe(viewLifecycleOwner, progressObserver)
            alertDialogLiveEvent.ld().observe(viewLifecycleOwner, alertDialogModelObserver)
            grantPermissionLiveEvent.ld().observe(viewLifecycleOwner, permissionObserver)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (viewModel is StateViewModel) {
            val bundle = SavedState()

            (viewModel as StateViewModel).onSaveState(bundle)
            outState.putSerialize(
                INSTANCE_STATE_KEY, bundle.getAll(),
                MapSerializer(String.serializer(), String.serializer())
            )
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val savedState = SavedState().apply {
            savedInstanceState?.getSerialize(
                INSTANCE_STATE_KEY, MapSerializer(String.serializer(), String.serializer())
            )?.let { addAll(it) }
        }
        if (viewModel is StateViewModel) {
            (viewModel as StateViewModel).onRestoreState(savedState)
        }
        super.onViewStateRestored(savedInstanceState)
    }

    override fun updateLanguage(context: Context) {
        super.updateLanguage(context)
        //viewModel.updateLanguage(context)
    }

    protected open fun loadingObserver(isLoading: Boolean) {}

    fun showErrorMsg(textMessage: BaseViewModel.TextMessage) {
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

    fun showInteractSnack(
        @StringRes text: Int,
        @StringRes clickButtonText: Int,
        onClick: () -> Unit
    ) {
        Snackbar.make(
            requireView(),
            getString(text),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(
            getString(clickButtonText)
        ) {
            onClick()
        }.show()
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
            isInProgress.removeObserver(progressObserver)
            alertDialogLiveEvent.removeObserver(alertDialogModelObserver)
            grantPermissionLiveEvent.removeObserver(permissionObserver)
        }
    }

    //TODO move it to baseFragment please
    protected fun ViewStubProxy.inflateViewStub() {
        if (!isInflated) {
            viewStub?.inflate()
        }
    }

    protected fun <M : ViewModel> getViewModelClass(): KClass<M> {
        return ReflectionUtils.getGenericParameterClass(
            this.javaClass,
            BaseVMFragment::class.java,
            1
        ).kotlin as KClass<M>
    }
}