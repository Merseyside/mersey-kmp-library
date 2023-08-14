package com.merseyside.merseyLib.archy.core.presentation.text

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

actual class TextRes actual constructor(stringDesc: StringDesc) : Text() {
    override fun getString(): String {
        TODO("Not yet implemented")
    }

    actual constructor(resource: StringResource) : this(StringDesc.Resource(resource))
}