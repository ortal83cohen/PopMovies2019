package com.cohen.popMovies2019


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView


class MoviesRecyclerViewAdapter(
    private val listViewModel: MoviesViewModel,
    private val activity: FragmentActivity

) : RecyclerView.Adapter<MovieViewHolder>() {
    private val listener = object : MovieViewHolder.Listener {
        override fun onMovieClick(movie: Movie, position: Int) {
            listViewModel.setItem(movie)
        }
    }

    init {
        listViewModel.getItems().observe(activity, Observer {
            notifyDataSetChanged()
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view, activity, listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        listViewModel.getItems().value?.get(position)?.let {
            holder.assignItem(it, position)
        }

    }

    override fun getItemCount(): Int = listViewModel.getItems().value?.size ?: 0


}
