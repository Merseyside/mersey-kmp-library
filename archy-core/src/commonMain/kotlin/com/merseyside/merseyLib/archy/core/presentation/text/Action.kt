package com.merseyside.merseyLib.archy.core.presentation.text

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

open class Action internal constructor() {
    lateinit var text: Text
    var action: () -> Unit = {}

    companion object {

        operator fun invoke(text: Text, action: () -> Unit = {}): Action {
            return Action().apply {
                this.text = text
                this.action = action
            }
        }
    }
}

fun Action.string(str: String) {
    text = TextString(str)
}

fun Action.stringResource(res: StringResource) {
    text = TextRes(StringDesc.Resource(res))
}

fun Action.stringDesc(stringDesc: StringDesc) {
    text = TextRes(stringDesc)
}
