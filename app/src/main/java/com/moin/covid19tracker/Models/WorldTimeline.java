package com.moin.covid19tracker.Models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class WorldTimeline {

    @SerializedName("cases")
    private HashMap<String,String> cases;

    @SerializedName("deaths")
    private HashMap<String,String> deaths;

    @SerializedName("recovered")
    private HashMap<String,String> recovered;

    public HashMap<String, String> getCases() {
        return cases;
    }

    public void setCases(HashMap<String, String> cases) {
        this.cases = cases;
    }

    public HashMap<String, String> getDeaths() {
        return deaths;
    }

    public void setDeaths(HashMap<String, String> deaths) {
        this.deaths = deaths;
    }

    public HashMap<String, String> getRecovered() {
        return recovered;
    }

    public void setRecovered(HashMap<String, String> recovered) {
        this.recovered = recovered;
    }
}
