package com.cohen.popMovies2019.ui


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessOnScrollListener(
    linearLayoutManager: RecyclerView.LayoutManager,
    private val mVisibleThreshold: Int
) : RecyclerView.OnScrollListener() {

    private val mLinearLayoutManager: LinearLayoutManager = linearLayoutManager as LinearLayoutManager
    private var lastCall: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = recyclerView.adapter!!.itemCount
        val firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        if (lastCall != totalItemCount && firstVisibleItem + mVisibleThreshold > lastCall) {
            lastCall = totalItemCount
            onLoadMore()
        }
    }

    fun reset() {
        lastCall = 0
    }

    abstract fun onLoadMore()
}