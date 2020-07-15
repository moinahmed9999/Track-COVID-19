package com.moin.covid19tracker.Models;

import com.google.gson.annotations.SerializedName;

public class CountryInfo {

    @SerializedName("flag")
    private String flag;

    @SerializedName("lat")
    private String lat;

    @SerializedName("long")
    private String long_;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return long_;
    }

    public void setLong(String long_) {
        this.long_ = long_;
    }

    @Override
    public String toString() {
        return "CountryInfo [flag = " + flag + ",  lat = " + lat + ", long = " + long_ + "]";
    }

}
