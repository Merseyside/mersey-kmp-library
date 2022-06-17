package com.merseyside.merseyLib.archy.android.di

import com.merseyside.merseyLib.kotlin.extensions.isZero
import com.merseyside.merseyLib.kotlin.extensions.logMsg
import org.koin.core.scope.Scope

interface SharedScopeInstance {

    val scope: Scope
    val sharedScope: Scope
    var linkedScopesCount: Int

    private fun linkToScope() {
        scope.linkTo(sharedScope)
        linkedScopesCount++
    }

    private fun unlinkScope() {
        scope.unlink(sharedScope)
        val count = ++linkedScopesCount

        if (count.isZero()) {
            sharedScope.close().also { logMsg("${this::class.simpleName}", "Scope closed!") }
        }
    }
}