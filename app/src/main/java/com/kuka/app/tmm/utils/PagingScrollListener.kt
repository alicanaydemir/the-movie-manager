package com.kuka.app.tmm.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuka.app.tmm.core.Constants

typealias OnPagingListener = (page: Int) -> Unit

class PagingScrollListener(
    private val visibleThreshold: Int = Constants.PAGE_SIZE,
    private val listener: OnPagingListener
) : RecyclerView.OnScrollListener() {

    private var loading = false
    private var previousItemCount = 0
    private var currentPage = 1

    private fun reset() {
        loading = false
        previousItemCount = 0
        currentPage = 1
    }

    init {
        reset()
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) return

        val layoutManager = recyclerView.layoutManager
        if (layoutManager !is LinearLayoutManager) return

        val itemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        when {
            loading && itemCount > previousItemCount -> {
                loading = false
            }
            !loading && lastVisibleItemPosition + visibleThreshold > itemCount -> {
                loading = true
                currentPage++
                listener(currentPage)
            }
        }

        previousItemCount = itemCount
    }
}

fun RecyclerView.addOnPagingListener(
    visibleThreshold: Int = Constants.PAGE_SIZE,
    listener: OnPagingListener
) = addOnScrollListener(PagingScrollListener(visibleThreshold, listener))