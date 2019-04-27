package com.cohen.popMovies2019

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    lateinit var moviesViewModel: MoviesViewModel
    private val detailFragment: DetailsFragment  by lazy {
        if (supportFragmentManager.findFragmentByTag("DetailsFragment") != null) {
            supportFragmentManager.findFragmentByTag("DetailsFragment") as DetailsFragment
        } else {
            DetailsFragment.newInstance()
        }
    }
    private val itemsListFragment: ItemsListFragment  by lazy {
        if (supportFragmentManager.findFragmentByTag("ItemsListFragment") != null) {
            supportFragmentManager.findFragmentByTag("ItemsListFragment") as ItemsListFragment
        } else {
            ItemsListFragment.newInstance()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        moviesViewModel.init(this as AppCompatActivity)
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
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, itemsListFragment, "itemsListFragment")
        transaction.commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setDetailMode() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, detailFragment, "detailFragment")
        transaction.commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        if (detailFragment.isVisible) {
            moviesViewModel.setItem(null)
        } else {
            super.onBackPressed()
        }
    }
}
