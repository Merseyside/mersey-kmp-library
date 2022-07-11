package com.merseyside.merseyLib.archy.android.presentation.extensions

import android.content.Context
import com.merseyside.merseyLib.archy.core.presentation.viewModel.entity.TextValue

fun TextValue.getString(context: Context): String {
    return this.text ?: this.stringDesc?.toString(context) ?: ""
}