package com.cohen.popMovies2019

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    lateinit var moviesViewModel: MoviesViewModel
    private val detailFragment = DetailsFragment.newInstance()
    private val itemsListFragment = ItemsListFragment.newInstance()

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
        if (!itemsListFragment.isVisible) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, itemsListFragment, LIST_FRAGMENT)
            transaction.commit()
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setDetailMode() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, detailFragment, DETAILS_FRAGMENT)
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
