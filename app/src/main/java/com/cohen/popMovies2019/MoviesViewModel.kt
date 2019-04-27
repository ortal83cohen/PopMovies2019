package com.cohen.popMovies2019

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cohen.popMovies2019.client.*
import com.squareup.okhttp.ResponseBody
import retrofit.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class MoviesViewModel : ViewModel() {

    private lateinit var mApi: Api
    private var items = MutableLiveData<ArrayList<Movie>>()
    private var selectedItem = MutableLiveData<Movie?>()
    private var page = MutableLiveData<Int>()

    private val mResultsCallback = object : RetrofitCallback<DiscoverResponse>() {
        override fun success(body: DiscoverResponse?, response: Response<DiscoverResponse>?) {
            val oldItems = items.value ?: ArrayList()
            oldItems.addAll(response?.body()?.results as ArrayList<Movie>)
            items.postValue(oldItems)
        }

        override fun failure(errorBody: ResponseBody?, isOffline: Boolean) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


    }

    fun init(appCompatActivity: AppCompatActivity) {
        val cfg = ApiConfig()
        cfg.isDebug = BuildConfig.DEBUG
        cfg.logger = RetrofitLogger()
        val httpClient = DefaultHttpClient(appCompatActivity)
        httpClient.setConnectTimeout(30000, TimeUnit.MILLISECONDS)
        httpClient.setReadTimeout(30000, TimeUnit.MILLISECONDS)
        httpClient.interceptors().add(RetrofitLogger.create())
        mApi = Api(cfg, httpClient)
        setItem(null)
        page.postValue(1)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        page.observe(appCompatActivity, androidx.lifecycle.Observer {
            mApi.getMovies(it, sdf.format(Date())).enqueue(mResultsCallback)
        })
    }

    fun getItems(): LiveData<ArrayList<Movie>> {
        return items
    }

    fun getItem(): LiveData<Movie?> {
        return selectedItem
    }

    fun setItem(movie: Movie?) {
        selectedItem.postValue(movie)
    }

    fun onLoadMore() {
        page.postValue(page.value?.plus(1) ?: 1)
    }

}
