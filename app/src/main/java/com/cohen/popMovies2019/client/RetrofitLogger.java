package com.cohen.popMovies2019.client;

import android.util.Log;


public class RetrofitLogger implements HttpLoggingInterceptor.Logger {

    public static HttpLoggingInterceptor create() {
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;//: HttpLoggingInterceptor.Level.BASIC;
        return new HttpLoggingInterceptor(new RetrofitLogger(), level);
    }

    @Override
    public void log(String message) {
        Log.d("RetrofitLogger", message);
    }
}
