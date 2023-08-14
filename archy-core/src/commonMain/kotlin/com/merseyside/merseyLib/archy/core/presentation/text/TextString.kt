package com.merseyside.merseyLib.archy.core.presentation.text

class TextString(private val str: String) : Text() {

    override fun getString(): String {
        return str
    }
}

