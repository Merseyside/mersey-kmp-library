package com.merseyside.merseyLib.archy.android.presentation.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.merseyLib.kotlin.utils.safeLet

abstract class PaginationUpDownScrollListener(
    private val loadItemsCountDownOffset: Int = 5,
    private val loadItemsCountUpOffset: Int = loadItemsCountDownOffset,
) : RecyclerView.OnChildAttachStateChangeListener {

    abstract val recyclerView: RecyclerView
    abstract val onLoadNextPageDown: () -> Unit
    abstract val onLoadNextPageUp: () -> Unit

    override fun onChildViewAttachedToWindow(view: View) {
        if (needToLoadDownNextPage(view)) onLoadNextPageDown()
        if (needToLoadUpNextPage(view)) onLoadNextPageUp()
    }

    override fun onChildViewDetachedFromWindow(view: View) {}

    private fun needToLoadDownNextPage(view: View): Boolean {
        with(recyclerView) {
            val lastPosition = getChildAdapterPosition(view)
            val itemCount = adapter?.itemCount
            return safeLet(itemCount) { counts ->
                (counts - lastPosition) <= loadItemsCountDownOffset
            } ?: false
        }
    }

    private fun needToLoadUpNextPage(view: View): Boolean {
        with(recyclerView) {
            val lastPosition = getChildAdapterPosition(view)
            return lastPosition == loadItemsCountUpOffset
        }
    }

}