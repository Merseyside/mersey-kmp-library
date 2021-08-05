package com.merseyside.merseyLib.utils.core

abstract class ObservableField<T> {
    var value: T? = null
        protected set(value) {
            field = value

            if (value != null) {
                if (observableList.isNotEmpty()) {
                    observableList.forEach { it(value) }
                }
            }
        }

    private val observableList: MutableList<(T) -> Unit> = mutableListOf()

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

class MutableObservableField<T>(initialValue: T? = null): ObservableField<T>() {
    init {
        value = initialValue
    }

    fun postValue(value: T) {
        this.value = value
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