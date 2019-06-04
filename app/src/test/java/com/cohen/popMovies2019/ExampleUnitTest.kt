package com.cohen.popMovies2019

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.cohen.popMovies2019.client.Api
import com.cohen.popMovies2019.data.Movie
import com.cohen.popMovies2019.data.MoviesViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.RETURNS_DEEP_STUBS
import org.mockito.Mockito.`when`
import java.text.SimpleDateFormat
import java.util.*


class ExampleUnitTest {
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    val moviesViewModel by lazy { MoviesViewModel() }

    inner class TestLifecycleOwner : LifecycleOwner {
        private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle {
            return mLifecycle
        }

        fun handleEvent(event: Lifecycle.Event) {
            mLifecycle.handleLifecycleEvent(event)
        }
    }

    @Test
    fun init() {
        val lifecycle = TestLifecycleOwner()
        val api = Mockito.mock(Api::class.java, RETURNS_DEEP_STUBS)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        `when`(api.getMovies(1, sdf.format(Date())).execute()).thenReturn(null)
        moviesViewModel.init(api, lifecycle)
        lifecycle.handleEvent(Lifecycle.Event.ON_CREATE)
        lifecycle.handleEvent(Lifecycle.Event.ON_START)

        assertEquals(moviesViewModel.getPage().value, 1)
    }

    @Test
    fun setMovieItem() {
        val movie = Movie(
            "1", "2", false, "4",
            5, "6", "7", "8", "9", 1.0f, 11, false, 1.2f
        )
        moviesViewModel.setItem(movie)

        assertEquals(moviesViewModel.getItem().value, movie)
    }

    @Test
    fun setSearchString() {

        moviesViewModel.setSearchString("Test1")

        assertEquals(moviesViewModel.getSearchString().value, "Test1")
    }

    @Test
    fun loadMore() {
        moviesViewModel.onLoadMore()
        assertEquals(moviesViewModel.getPage().value, 1)
        moviesViewModel.onLoadMore()
        assertEquals(moviesViewModel.getPage().value, 2)
    }
}
