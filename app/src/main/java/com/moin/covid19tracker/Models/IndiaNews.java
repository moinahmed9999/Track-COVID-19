package com.moin.covid19tracker.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IndiaNews {

    @SerializedName("results")
    private List<NewsResult> results;

    public List<NewsResult> getResults ()
    {
        return results;
    }

    public void setResults (List<NewsResult> results)
    {
        this.results = results;
    }
}
