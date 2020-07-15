package com.moin.covid19tracker.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IndianStateDistrictWise {
    @SerializedName("districtData")
    private List<DistrictData> districtData;

    @SerializedName("state")
    private String state;

    public List<DistrictData> getDistrictData() {
        return districtData;
    }

    public void setDistrictData(List<DistrictData> districtData) {
        this.districtData = districtData;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "IndianStateDistrictWise [districtData = " + districtData + ", state = " + state + "]";
    }
}
