package com.merseyside.merseyLib.archy.core.presentation.message

import com.merseyside.merseyLib.archy.core.presentation.text.Action
import com.merseyside.merseyLib.archy.core.presentation.text.Text
import com.merseyside.merseyLib.archy.core.presentation.text.TextRes
import com.merseyside.merseyLib.archy.core.presentation.text.TextString
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

abstract class Message

sealed class TypedMessage(
    val text: Text,
    val action: Action?
): Message() {

    class InfoMessage(
        text: Text,
        action: Action? = null
    ) : TypedMessage(text, action) {

        companion object {
            operator fun invoke(str: String): InfoMessage {
                return InfoMessage(TextString(str))
            }

            operator fun invoke(str: String, action: Action.() -> Unit): InfoMessage {
                val action = Action().apply(action)
                return InfoMessage(TextString(str), action)
            }

            operator fun invoke(resource: StringResource): InfoMessage {
                return InfoMessage(TextRes(StringDesc.Resource(resource)))
            }

            operator fun invoke(resource: StringResource, action: Action.() -> Unit): InfoMessage {
                val action = Action().apply(action)
                return InfoMessage(TextRes(StringDesc.Resource(resource)), action)
            }

            operator fun invoke(stringDesc: StringDesc): InfoMessage {
                return InfoMessage(TextRes(stringDesc))
            }

            operator fun invoke(stringDesc: StringDesc, action: Action.() -> Unit): InfoMessage {
                val action = Action().apply(action)
                return InfoMessage(TextRes(stringDesc), action)
            }
        }
    }

    class ErrorMessage(
        text: Text,
        action: Action? = null
    ): TypedMessage(text, action) {
        companion object {

            operator fun invoke(throwable: Throwable): ErrorMessage {
                return ErrorMessage(TextString(throwable.message ?: "Something went wrong"))
            }

            operator fun invoke(str: String): ErrorMessage {
                return ErrorMessage(TextString(str))
            }

            operator fun invoke(str: String, action: Action.() -> Unit): ErrorMessage {
                val action = Action().apply(action)
                return ErrorMessage(TextString(str), action)
            }

            operator fun invoke(resource: StringResource): ErrorMessage {
                return ErrorMessage(TextRes(StringDesc.Resource(resource)))
            }

            operator fun invoke(resource: StringResource, action: Action.() -> Unit): ErrorMessage {
                val action = Action().apply(action)
                return ErrorMessage(TextRes(StringDesc.Resource(resource)), action)
            }

            operator fun invoke(stringDesc: StringDesc): ErrorMessage {
                return ErrorMessage(TextRes(stringDesc))
            }

            operator fun invoke(stringDesc: StringDesc, action: Action.() -> Unit): ErrorMessage {
                val action = Action().apply(action)
                return ErrorMessage(TextRes(stringDesc), action)
            }
        }
    }
}