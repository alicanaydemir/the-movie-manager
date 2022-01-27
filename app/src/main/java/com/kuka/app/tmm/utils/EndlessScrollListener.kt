package com.kuka.app.tmm.utils

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener constructor(private val linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private val visibleThreshold = 4
    private var currentItemCount: Int = 0
    private var currentPage = 1
    private var loading = true
    var totalItemCount: Int? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        currentItemCount = linearLayoutManager.itemCount

        if (currentItemCount < previousTotal) {
            reset()
        }

        if (loading && isTotalItemCountRecentlyIncreased()) {
            loading = false
            previousTotal = currentItemCount
        }

        if (shouldLoadNextPage(visibleThreshold)) {
            currentPage++
            recyclerView.post { onLoadMore(currentPage) }
            loading = true
        }
    }

    fun setCurrentPage(current:Int){
        currentPage=current
    }

    private fun isTotalItemCountRecentlyIncreased(): Boolean {
        return currentItemCount > previousTotal + visibleThreshold
    }

    private fun shouldLoadNextPage(threshold: Int): Boolean {
        return !loading && isLastVisibleItemPositionExceedsTotalItemCount(threshold) && !isLastPage()
    }

    private fun isLastVisibleItemPositionExceedsTotalItemCount(threshold: Int): Boolean {
        return linearLayoutManager.findLastVisibleItemPosition() + threshold >= currentItemCount
    }

    private fun isLastPage(): Boolean {
        return currentItemCount >= totalItemCount ?: 0
    }

    private fun reset() {
        previousTotal = 0
        loading = true
        currentPage = 1
    }

    abstract fun onLoadMore(page: Int)
}
