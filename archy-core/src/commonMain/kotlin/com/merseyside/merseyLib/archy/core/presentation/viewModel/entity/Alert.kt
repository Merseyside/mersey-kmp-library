package com.merseyside.merseyLib.archy.core.presentation.viewModel.entity

data class Alert(
    val title: String? = null,
    val message: String? = null,
    val positiveButtonText: String? = null,
    val negativeButtonText: String? = null,
    val onPositiveClick: () -> Unit = {},
    val onNegativeClick: () -> Unit = {},
    val isSingleAction: Boolean? = null,
    val isCancelable: Boolean? = null
)