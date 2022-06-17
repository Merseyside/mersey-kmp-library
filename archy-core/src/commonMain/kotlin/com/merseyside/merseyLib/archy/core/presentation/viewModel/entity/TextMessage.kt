package com.merseyside.merseyLib.archy.core.presentation.viewModel.entity

data class TextMessage(
    val isError: Boolean = false,
    var msg: String = "",
    var actionMsg: String? = null,
    val onClick: () -> Unit = {}
)