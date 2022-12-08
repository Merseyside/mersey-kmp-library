package com.merseyside.merseyLib.archy.android.presentation.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.merseyside.merseyLib.kotlin.utils.safeLet

abstract class PaginationScrollListener(
    private val loadItemsCountOffset: Int = 5
) : RecyclerView.OnChildAttachStateChangeListener {

    abstract val recyclerView: RecyclerView
    abstract val onLoadNextPage: () -> Unit

    override fun onChildViewAttachedToWindow(view: View) {
        if (needToLoadNextPage(view)) onLoadNextPage()
    }

    override fun onChildViewDetachedFromWindow(view: View) {}

    private fun needToLoadNextPage(view: View): Boolean {
        with(recyclerView) {
            val lastPosition = getChildAdapterPosition(view)
            val itemCount = adapter?.itemCount
            return safeLet(itemCount) {
                (it - lastPosition) <= loadItemsCountOffset
            } ?: false
        }
    }
}