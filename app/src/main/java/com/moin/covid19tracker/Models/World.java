package com.moin.covid19tracker.Models;

import com.google.gson.annotations.SerializedName;

public class World {

    public World(String recovered, String cases, String active, String deaths) {
        this.recovered = recovered;
        this.cases = cases;
        this.active = active;
        this.deaths = deaths;
    }

    @SerializedName("recovered")
    private String recovered;

    @SerializedName("cases")
    private String cases;

    @SerializedName("active")
    private String active;

    @SerializedName("deaths")
    private String deaths;

    public String getRecovered() {
        return recovered;
    }

    public String getCases() {
        return cases;
    }

    public String getActive() {
        return active;
    }

    public String getDeaths() {
        return deaths;
    }

    @Override
    public String toString() {
        return "WorldPojo [recovered = " + recovered + ", cases = " + cases + ", active = " + active + ",  deaths = " + deaths + "]";
    }
}
