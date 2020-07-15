package com.moin.covid19tracker.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorldApiServiceProvider {
    private static final String WORLD_BASE_URL = "https://corona.lmao.ninja";

    private static WorldApiService sMWorldApiService;

    public static WorldApiService getWorldApiService() {
        if (sMWorldApiService ==null) {
            OkHttpClient mOkHttpClient = new OkHttpClient
                    .Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
            Retrofit mRetrofit = new Retrofit.Builder()
                    .baseUrl(WORLD_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkHttpClient)
                    .build();
            sMWorldApiService = mRetrofit.create(WorldApiService.class);
        }
        return sMWorldApiService;
    }

}
