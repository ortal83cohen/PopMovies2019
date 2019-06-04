package com.cohen.popMovies2019.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.cohen.popMovies2019.data.MoviesViewModel
import com.cohen.popMovies2019.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : androidx.fragment.app.Fragment() {
    private val IMAGE_BASE_URL_SMALL = "http://image.tmdb.org/t/p/w185"
    lateinit var moviesViewModel: MoviesViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_details, container, false)
        moviesViewModel = ViewModelProviders.of(activity!!).get(MoviesViewModel::class.java)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesViewModel.getItem().value?.apply {
            Picasso.with(activity)
                .load(IMAGE_BASE_URL_SMALL + poster_path)
                .fit().centerCrop()
                .into(detailImage)
            detailTitle.text = title
            text1.text = release_date
            text2.text = "$vote_average/10"
            detailOverview.text = overview
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            DetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
