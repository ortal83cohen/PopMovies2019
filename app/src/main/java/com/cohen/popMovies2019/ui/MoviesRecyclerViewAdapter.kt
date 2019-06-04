package com.cohen.popMovies2019.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.cohen.popMovies2019.data.Movie
import com.cohen.popMovies2019.data.MoviesViewModel
import com.cohen.popMovies2019.R


class MoviesRecyclerViewAdapter(
    private val moviesViewModel: MoviesViewModel,
    private val activity: FragmentActivity

) : RecyclerView.Adapter<MovieViewHolder>() {
    private val listener = object : MovieViewHolder.Listener {
        override fun onMovieClick(movie: Movie, position: Int) {
            moviesViewModel.setItem(movie)
        }
    }

    init {
        moviesViewModel.getItems().observe(activity, Observer {
            notifyDataSetChanged()
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view, activity, listener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        moviesViewModel.getItems().value?.get(position)?.let {
            holder.assignItem(it, position)
        }

    }

    override fun getItemCount(): Int = moviesViewModel.getItems().value?.size ?: 0


}
