package com.merseyside.sample.manager

import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.utils.core.savedState.SavedState
import com.merseyside.merseyLib.utils.core.savedState.delegate.valueOrNull

class SomeManager(savedState: SavedState) {

    var nameWithAge by savedState.valueOrNull<String>()
}