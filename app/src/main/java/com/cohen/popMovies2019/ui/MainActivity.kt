package com.cohen.popMovies2019.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cohen.popMovies2019.BuildConfig
import com.cohen.popMovies2019.R
import com.cohen.popMovies2019.client.Api
import com.cohen.popMovies2019.client.ApiConfig
import com.cohen.popMovies2019.client.DefaultHttpClient
import com.cohen.popMovies2019.client.RetrofitLogger
import com.cohen.popMovies2019.data.MoviesViewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var moviesViewModel: MoviesViewModel
    private val detailFragment = DetailsFragment.newInstance()
    private val itemsListFragment = ItemsListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)

       val httpClient =   DefaultHttpClient(this)
        httpClient.apply {
            setConnectTimeout(30000, TimeUnit.MILLISECONDS)
            setReadTimeout(30000, TimeUnit.MILLISECONDS)
            interceptors().add(RetrofitLogger.create())
        }
        val cfg = ApiConfig()
        cfg.isDebug = BuildConfig.DEBUG
        cfg.logger = RetrofitLogger()
        val api = Api(cfg, httpClient)
        moviesViewModel.init(api, this)
        moviesViewModel.getItem().observe(this, Observer {
            if (it == null) {
                setListMode()
            } else {
                setDetailMode()
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setListMode() {
        if (!itemsListFragment.isVisible) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(
                R.id.fragment_container, itemsListFragment,
                LIST_FRAGMENT
            )
            transaction.commit()
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setDetailMode() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(
            R.id.fragment_container, detailFragment,
            DETAILS_FRAGMENT
        )
        transaction.addToBackStack(DETAILS_FRAGMENT)
        transaction.commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
            moviesViewModel.setItem(null)
            super.onBackPressed()
    }

    companion object {
        const val DETAILS_FRAGMENT = "detailFragment"
        const val LIST_FRAGMENT = "itemsListFragment"
    }
}
