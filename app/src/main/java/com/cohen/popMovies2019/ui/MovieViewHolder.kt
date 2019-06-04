package com.cohen.popMovies2019.ui


import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cohen.popMovies2019.R
import com.cohen.popMovies2019.data.Movie
import com.squareup.picasso.Picasso

class MovieViewHolder(v: View, private val mContext: Context, private val mListener: Listener) :
    RecyclerView.ViewHolder(v) {

    var mImageView: ImageView = v.findViewById(R.id.image)
    var mTitleView: TextView = v.findViewById(R.id.title)
    private var mItem: Movie? = null
    private var mPosition: Int = 0

    fun assignItem(item: Movie, position: Int) {
        mPosition = position
        itemView.setOnClickListener { mListener.onMovieClick(item, position) }
        mItem = item
        mItem?.poster_path?.let {
            Picasso.with(mContext)
                .load(IMAGE_BASE_URL_SMALL + it)
                .fit().centerCrop()
                .into(mImageView)
        }
        mTitleView.text = mItem!!.title
    }

    interface Listener {
        fun onMovieClick(acc: Movie, position: Int)
    }

    companion object {

        private val IMAGE_BASE_URL_SMALL = "http://image.tmdb.org/t/p/w185"
    }

}