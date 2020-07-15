package com.moin.covid19tracker.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class India {

    @SerializedName("cases_time_series")
    private List<IndiaDaily> cases_time_series;

    @SerializedName("statewise")
    private List<IndianState> statewise;

    public List<IndiaDaily> getCases_time_series() {
        return cases_time_series;
    }

    public void setCases_time_series(List<IndiaDaily> cases_time_series) {
        this.cases_time_series = cases_time_series;
    }

    public List<IndianState> getStatewise() {
        return statewise;
    }

    public void setStatewise(List<IndianState> statewise) {
        this.statewise = statewise;
    }

    @Override
    public String toString() {
        return "India [cases_time_series = " + cases_time_series + ", statewise = " + statewise + "]";
    }

}
