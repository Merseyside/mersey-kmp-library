package com.merseyside.merseyLib.archy.core.presentation.text

import android.content.Context
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class TextRes actual constructor(val stringDesc: StringDesc) : Text(),
    KoinComponent {

    private val context: Context by inject()

    override fun getString(): String {
        return stringDesc.toString(context)
    }

    actual constructor(resource: StringResource) : this(StringDesc.Resource(resource))
}