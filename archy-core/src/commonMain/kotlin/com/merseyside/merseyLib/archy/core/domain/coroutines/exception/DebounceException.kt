package com.merseyside.merseyLib.archy.core.domain.coroutines.exception

import kotlinx.coroutines.CancellationException

class DebounceException(msg: String) : CancellationException(msg)