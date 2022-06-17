package com.merseyside.sample.view

import android.content.Context
import com.merseyside.merseyLib.archy.android.presentation.fragment.VMEventsFragment
import com.merseyside.sample.BR
import com.merseyside.sample.R
import com.merseyside.sample.databinding.FragmentTestBinding
import com.merseyside.sample.viewModel.TestViewModel

class TestFragment : VMEventsFragment<FragmentTestBinding, TestViewModel,
        TestViewModel.TestEventsListener>(), TestViewModel.TestEventsListener {
    override fun getLayoutId() = R.layout.fragment_test
    override fun getTitle(context: Context) = null
    override fun getBindingVariable() = BR.viewModel
    override fun doSomeJob() {
        showMsg("Doing some job!")
    }
}