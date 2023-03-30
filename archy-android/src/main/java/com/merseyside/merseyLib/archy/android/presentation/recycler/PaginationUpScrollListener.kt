package com.merseyside.merseyLib.archy.android.presentation.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationUpScrollListener(
    loadItemsCountUpOffset: Int = 5,
) : PaginationUpDownScrollListener(loadItemsCountDownOffset = 0, loadItemsCountUpOffset) {

    override val onLoadNextPageDown: () -> Unit = {}

    override fun onChildViewAttachedToWindow(view: View) {
        if (needToLoadUpNextPage(view)) onLoadNextPageUp()
    }

    override fun onChildViewDetachedFromWindow(view: View) {}

    private fun needToLoadUpNextPage(view: View): Boolean {
        with(recyclerView) {
            val lastPosition = getChildAdapterPosition(view)
            return lastPosition == loadItemsCountUpOffset
        }
    }

}