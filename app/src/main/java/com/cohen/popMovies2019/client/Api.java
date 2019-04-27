package com.cohen.popMovies2019.client;

import android.util.ArrayMap;
import com.squareup.okhttp.*;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

public class Api {

    public static final String PATH_MOVIES = "/3/discover/movie";
    public static final String PATH_MOVIE = "/3/movie/{movie_id}";
    private OkHttpClient mHttpClient;

    private ApiConfig mConfig;

    private Interceptor mRequestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            HttpUrl url = request.httpUrl().newBuilder()
                    .addQueryParameter("api_key", mConfig.getApiKey())
                    .build();


            return chain.proceed(request.newBuilder().url(url).build());
        }
    };

    public Api(ApiConfig config) {
        this(config, null);
    }

    public Api(ApiConfig config, OkHttpClient httpClient) {
        mConfig = config;
        mHttpClient = httpClient == null ? new OkHttpClient() : httpClient;
        mHttpClient.interceptors().add(0, mRequestInterceptor);

    }

    public ApiConfig getConfig() {
        return mConfig;
    }

    private Service create() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(mHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mConfig.getEndpoint());

        Retrofit restAdapter = builder.build();

        return restAdapter.create(Service.class);
    }

    public Call<DiscoverResponse> getMovies(int page, String today) throws InvalidParameterException {

        Service service = create();

        ArrayMap<String, String> query = new ArrayMap<>();

        query.put("page", String.valueOf(page));
        query.put("sort_by", "release_date.desc");
        query.put("release_date.lte", today);

        return service.movies(query);
    }

    public Call<MovieResponse> getMovie(int id) throws InvalidParameterException {

        Service service = create();

        return service.movie(id);
    }


    public interface Service {

        @GET(PATH_MOVIES)
        Call<DiscoverResponse> movies(@QueryMap Map<String, String> query);

        @GET(PATH_MOVIE)
        Call<MovieResponse> movie(@Path("movie_id") int movieId);

    }

}
