package com.merseyside.merseyLib.utils.core.ext

import com.merseyside.merseyLib.kotlin.extensions.isNotZero
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun Long?.isNotNullAndZero(): Boolean {
    contract {
        returns(true) implies (this@isNotNullAndZero != null)
    }

    return this != null && this.isNotZero()
}