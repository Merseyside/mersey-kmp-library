package com.merseyside.sample.view

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.merseyside.archy.presentation.activity.BaseBindingActivity
import com.merseyside.sample.R
import com.merseyside.sample.databinding.ActivityMainBinding
import com.merseyside.sample.notifications.NotificationTest
import com.merseyside.utils.view.ext.onClick
import org.koin.android.ext.android.inject

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private val test by inject<NotificationTest>()

    override fun getFragmentContainer(): Int? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getToolbar(): Toolbar? {
        return null
    }

    override fun performInjection(bundle: Bundle?, vararg params: Any) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireBinding().showNotification.onClick { test.showNotification() }
    }
}