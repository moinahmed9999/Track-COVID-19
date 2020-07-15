package com.moin.covid19tracker.Network;

import com.moin.covid19tracker.Models.IndiaNews;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IndiaNewsApiService {
    @GET("news")
    Call<IndiaNews> getIndiaNews();
}
