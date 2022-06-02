package com.merseyside.merseyLib.utils.core.state

import com.merseyside.merseyLib.kotlin.extensions.forEachEntry

fun SavedState.forEach(block: (String, Any) -> Unit) {
    container.forEachEntry(block)
}