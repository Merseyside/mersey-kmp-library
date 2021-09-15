package com.merseyside.merseyLib.archy.android.presentation.activity

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.merseyside.archy.presentation.activity.BaseBindingActivity
import com.merseyside.merseyLib.archy.core.presentation.model.BaseViewModel
import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel
import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel.Companion.INSTANCE_STATE_KEY
import com.merseyside.merseyLib.utils.core.SavedState
import com.merseyside.utils.reflection.ReflectionUtils
import com.merseyside.utils.requestPermissions
import com.merseyside.utils.serialization.putSerialize
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

abstract class BaseVMActivity<B : ViewDataBinding, M : BaseViewModel>
    : BaseBindingActivity<B>(), KoinScopeComponent {

    override val scope: Scope by activityScope()

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
            requestPermissions(*pair.first, requestCode = pair.second)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindingVariable()

        //viewModel.updateLanguage(this)

        observeViewModel()
    }

    override fun performInjection(bundle: Bundle?) {
        viewModel = scope.getViewModel(
            owner = { ViewModelOwner.from(this)},
            clazz = persistentClass,
            parameters = { parametersOf(bundle) }
        )
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

        if (viewModel is StateViewModel) {
            val savedState = SavedState()

            (viewModel as StateViewModel).onSaveState(savedState)
            outState.putSerialize(
                INSTANCE_STATE_KEY,
                savedState.getAll(),
                MapSerializer(String.serializer(), String.serializer())
            )
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            messageLiveEvent.addObserver(messageObserver)
            isInProgress.addObserver(loadingObserver)
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
        ReflectionUtils.getGenericParameterClass(this.javaClass, BaseVMActivity::class.java, 1).kotlin as KClass<M>
}
