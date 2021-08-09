package com.merseyside.merseyLib.utils.core

abstract class ObservableField<T> {
    abstract val value: T?

    protected val observableList: MutableList<(T) -> Unit> = mutableListOf()

    fun observe(block: (T) -> Unit): Disposable<T> {
        observableList.add(block)
        value?.let {
            block(it)
        }

        return Disposable(this, block)
    }

    fun removeObserver(block: (T) -> Unit) {
        observableList.remove(block)
    }

    fun removeAllObservers() { observableList.clear() }
}

open class MutableObservableField<T>(initialValue: T? = null): ObservableField<T>() {

    override var value: T? = initialValue
        set(value) {
            field = value

            if (value != null) {
                if (observableList.isNotEmpty()) {
                    observableList.forEach { it(value) }
                }
            }
        }
}

class SingleObservableField<T>(initialValue: T? = null): ObservableField<T>() {
    override var value: T? = initialValue
        get() {
            return field.also { value = null }
        }
        set(value) {
            field = value

            if (value != null) {
                if (observableList.isNotEmpty()) {
                    val v = this.value ?: return
                    observableList.forEach { it(v) }
                }
            }
        }
}

class Disposable<T>(
    private val field: ObservableField<T>,
    private val observer: (T) -> Unit
) {
    fun dispose() {
        field.removeObserver(observer)
    }
}