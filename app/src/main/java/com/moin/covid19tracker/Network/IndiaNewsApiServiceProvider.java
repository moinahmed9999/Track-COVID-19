package com.moin.covid19tracker.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IndiaNewsApiServiceProvider {

    private static final String INDIA_NEWS_BASE_URL = "https://api-covid19-in.herokuapp.com/";

    private static IndiaNewsApiService sIndiaNewsApiService;

    public static IndiaNewsApiService getIndiaNewsApiService() {
        if (sIndiaNewsApiService==null) {
            OkHttpClient mOkHttpClient = new OkHttpClient
                    .Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
            Retrofit mRetrofit = new Retrofit.Builder()
                    .baseUrl(INDIA_NEWS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkHttpClient)
                    .build();
            sIndiaNewsApiService = mRetrofit.create(IndiaNewsApiService.class);
        }
        return sIndiaNewsApiService;
    }
}
