package com.merseyside.merseyLib.archy.core.presentation.viewModel.entity

data class TextMessage(
    val isError: Boolean = false,
    var msg: TextValue = TextValue(""),
    var actionMsg: TextValue? = null,
    val onClick: () -> Unit = {}
)