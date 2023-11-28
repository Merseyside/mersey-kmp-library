package com.merseyside.merseyLib.archy.core.presentation.viewModel.entity

open class Alert(
    val title: TextValue? = null,
    val message: TextValue? = null,
    val positiveButtonText: TextValue? = null,
    val negativeButtonText: TextValue? = null,
    val onPositiveClick: () -> Unit = {},
    val onNegativeClick: () -> Unit = {},
    val isSingleAction: Boolean? = null,
    val isCancelable: Boolean? = null
)