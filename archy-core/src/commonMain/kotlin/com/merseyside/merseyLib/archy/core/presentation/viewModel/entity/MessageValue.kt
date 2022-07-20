package com.merseyside.merseyLib.archy.core.presentation.viewModel.entity

import dev.icerock.moko.resources.desc.StringDesc

data class TextValue private constructor(
    val text: String?,
    val stringDesc: StringDesc?
) {
    constructor(text: String) : this(text, null)
    constructor(stringDesc: StringDesc) : this(null, stringDesc)
}