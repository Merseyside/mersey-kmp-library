package com.merseyside.merseyLib.archy.core.presentation.text

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc

expect class TextRes(stringDesc: StringDesc): Text {

    constructor(resource: StringResource)
}