package com.cohen.popMovies2019.ui

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cohen.popMovies2019.data.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_item_list.*


class ItemsListFragment : androidx.fragment.app.Fragment() {

    lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.cohen.popMovies2019.R.layout.fragment_item_list, container, false)

        moviesViewModel = ViewModelProviders.of(activity!!).get(MoviesViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        endlessRecyclerView?.apply {
            setOnLoadMoreListener {
                moviesViewModel.onLoadMore()
            }
            init(LinearLayoutManager(context),
                MoviesRecyclerViewAdapter(moviesViewModel, activity!!), 4)
            setHasMoreData(true)
        }

        autoCompleteTextView?.apply {
            threshold = 1
            setAdapter<ArrayAdapter<String>>(
                AutoCompleteAdapter(
                    context,
                    R.layout.select_dialog_item,
                    moviesViewModel
                )
            )
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    moviesViewModel.setSearchString(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            })
        }
        moviesViewModel.getSearchString().observe(context as LifecycleOwner, Observer {
            endlessRecyclerView?.setHasMoreData(it.isNullOrEmpty())
        })
    }


    companion object {


        @JvmStatic
        fun newInstance() =
            ItemsListFragment()
    }
}
