package com.cohen.popMovies2019

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager


class ItemsListFragment : androidx.fragment.app.Fragment() {

    lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        moviesViewModel = ViewModelProviders.of(activity!!).get(MoviesViewModel::class.java)

        (view as EndlessRecyclerView).apply {
            setOnLoadMoreListener {
                moviesViewModel.onLoadMore()
            }
            init(LinearLayoutManager(context), MoviesRecyclerViewAdapter(moviesViewModel, activity!!), 4)
            setHasMoreData(true)
        }

        return view
    }


    companion object {


        @JvmStatic
        fun newInstance() =
            ItemsListFragment()
    }
}
