package com.moin.covid19tracker.Models;

import com.google.gson.annotations.SerializedName;

public class DistrictData implements Comparable<DistrictData>{
    @SerializedName("district")
    private String district;

    @SerializedName("confirmed")
    private String confirmed;

    public DistrictData(String district, String confirmed) {
        this.district = district;
        this.confirmed = confirmed;
    }

    private Integer confiredCases;  // Used for sorting

    public Integer getConfiredCases() {
        return confiredCases;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public String toString() {
        return "DistrictData [district = " + district + ", confirmed = " + confirmed + "]";
    }

    @Override
    public int compareTo(DistrictData o) {
        this.confiredCases = Integer.parseInt(this.getConfirmed());
        o.confiredCases = Integer.parseInt(o.getConfirmed());
        return this.getConfiredCases().compareTo(o.getConfiredCases());
    }
}
