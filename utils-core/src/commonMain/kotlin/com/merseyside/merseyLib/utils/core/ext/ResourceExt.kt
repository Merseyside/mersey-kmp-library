package com.merseyside.merseyLib.utils.core.ext

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

fun getString(res: StringResource, vararg args: String): String {
    return StringDesc.ResourceFormatted(res, *args).toString()
}

fun getStringNull(res: StringResource?, vararg args: String): String? {
    return res?.let { StringDesc.ResourceFormatted(res, *args).toString() }
}