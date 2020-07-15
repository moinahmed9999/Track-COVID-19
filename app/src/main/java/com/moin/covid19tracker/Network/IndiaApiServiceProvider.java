package com.moin.covid19tracker.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IndiaApiServiceProvider {

    private static final String INDIA_BASE_URL = "https://api.covid19india.org";

    private static IndiaApiService sIndiaApiService;

    public static IndiaApiService getIndiaApiService() {
        if (sIndiaApiService ==null) {
            OkHttpClient mOkHttpClient = new OkHttpClient
                    .Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
            Retrofit mRetrofit = new Retrofit.Builder()
                    .baseUrl(INDIA_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkHttpClient)
                    .build();
            sIndiaApiService = mRetrofit.create(IndiaApiService.class);
        }
        return sIndiaApiService;
    }

}
