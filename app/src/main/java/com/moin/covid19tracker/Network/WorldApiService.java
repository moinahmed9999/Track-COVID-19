package com.moin.covid19tracker.Network;

import com.moin.covid19tracker.Models.Country;
import com.moin.covid19tracker.Models.CountryTimeline;
import com.moin.covid19tracker.Models.World;
import com.moin.covid19tracker.Models.WorldTimeline;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WorldApiService {

    @GET("/v3/covid-19/all")
    Call<World> getWorld();

    @GET("/v3/covid-19/countries?sort=cases")
    Call<List<Country>> getCountriesByCases();

    @GET("/v3/covid-19/historical/all?lastdays=all")
    Call<WorldTimeline> getWorldStats();

    @GET("/v3/covid-19/historical/{country}?lastdays=all")
    Call<CountryTimeline> getCountryStats(@Path("country") String country);

}
