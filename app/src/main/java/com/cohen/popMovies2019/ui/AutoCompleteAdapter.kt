package com.cohen.popMovies2019.ui

import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.cohen.popMovies2019.data.MoviesViewModel

open class AutoCompleteAdapter(context: Context, resource: Int, moviesViewModel: MoviesViewModel) :
    ArrayAdapter<String>(context, resource) {

    init {
        moviesViewModel.getItems().observe(context as LifecycleOwner, Observer {
            it.forEach { movie ->
                add(movie.title)
            }
            notifyDataSetChanged()
        })
    }

//    override fun getCount(): Int {
//        return moviesViewModel.getItems().value?.count() ?: 0
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        return super.getView(position, convertView, parent)
//    }


}