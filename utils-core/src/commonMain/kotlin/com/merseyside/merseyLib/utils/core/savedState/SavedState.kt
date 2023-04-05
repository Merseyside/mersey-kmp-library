package com.merseyside.merseyLib.utils.core.savedState

import com.merseyside.merseyLib.utils.core.savedState.validator.validateSavedStateValue

@Suppress("UNCHECKED_CAST")
class SavedState {

    internal val container: MutableMap<String, Any?> = mutableMapOf()

    fun contains(key: String): Boolean {
        return container.contains(key)
    }

    fun put(key: String, value: Any?) {
        if (validateSavedStateValue(value)) {
            container[key] = value
        } else throw IllegalArgumentException("Can not put value cause value's type is not supported. Look for serializable.")
    }

    operator fun <T> get(key: String): T? {
        return try {
            container[key] as T
        } catch (e: ClassCastException) {
            // Instead of failing on ClassCastException, we remove the value from the
            // SavedStateHandle and return null.
            remove<T>(key)
        }
    }

    fun <T> get(key: String, defValue: T): T {
        return get(key) ?: defValue
    }


    fun <T> remove(key: String): T? {
        return container.remove(key) as T?
    }

    internal fun <T> getOrPut(key: String, value: T): T {
        return container.getOrPut(key) { value } as T
    }

    fun getSavedState(key: String): SavedState? {
        return if (contains(key)) {
            val value = get<SavedState>(key)
            value
                ?: throw IllegalArgumentException("Value by $key key is not SavedState! \n $value")
        } else null
    }

    override fun toString(): String {
        val str = container.map { "${it.key} : ${it.value}" }.joinToString(separator = "\n")
        return str.ifEmpty { "Saved state is empty!" }
    }
}