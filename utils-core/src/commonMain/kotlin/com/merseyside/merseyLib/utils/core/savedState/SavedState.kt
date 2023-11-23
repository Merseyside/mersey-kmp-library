package com.merseyside.merseyLib.utils.core.savedState

import com.merseyside.merseyLib.utils.core.savedState.validator.validateSavedStateValue

@Suppress("UNCHECKED_CAST")
class SavedState {

    internal val container: MutableMap<String, Any?> = mutableMapOf()

    private var onPreSaveStateCallback: OnPreSaveStateCallback? = null

    fun contains(key: String): Boolean {
        return container.contains(key)
    }

    fun <T> put(key: String, value: T?): T? {
        return if (validateSavedStateValue(value)) {
            container[key] = value
            value
        } else throw IllegalArgumentException(
            "Can not put value cause value's type is not supported. " +
                    "Wait for serializable."
        )
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
        return get(key) ?: run {
            put(key, defValue)
            defValue
        }
    }

    fun <T> remove(key: String): T? {
        return container.remove(key) as T?
    }

    internal fun <T> getOrPut(key: String, value: T): T {
        return container.getOrPut(key) { value } as T
    }

    fun getSavedState(key: String): SavedState {
        val value = get<SavedState>(key)
        return value ?: SavedState().also { state -> put(key, state) }
    }

    fun isEmpty(): Boolean {
        return container.isEmpty()
    }

    fun clear() {
        container.clear()
    }

    fun setOnPreSaveStateCallback(callback: OnPreSaveStateCallback?) {
        this.onPreSaveStateCallback = callback
    }

    override fun toString(): String {
        return if (container.isNotEmpty()) {
            val builder = StringBuilder()
            container.forEach { (key, value) ->
                builder.append(key)
                builder.append(": ")
                if (value is SavedState) builder.append("\n    ")
                builder.append(value)
            }

            builder.toString()
        } else {
            "Saved state is empty!"
        }
    }

    fun preSave() {
        val innerSaveStates = container.values.filterIsInstance<SavedState>()
        innerSaveStates.forEach { savedState -> savedState.preSave() }
        onPreSaveStateCallback?.onPreSaveState(this)
    }

    fun interface OnPreSaveStateCallback {

        fun onPreSaveState(savedState: SavedState)
    }
}