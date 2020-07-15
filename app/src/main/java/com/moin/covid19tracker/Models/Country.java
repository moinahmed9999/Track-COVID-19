package com.moin.covid19tracker.Models;

import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("country")
    private String country;

    @SerializedName("recovered")
    private String recovered;

    @SerializedName("cases")
    private String cases;

    @SerializedName("critical")
    private String critical;

    @SerializedName("deathsPerOneMillion")
    private String deathsPerOneMillion;

    @SerializedName("active")
    private String active;

    @SerializedName("casesPerOneMillion")
    private String casesPerOneMillion;

    @SerializedName("countryInfo")
    private CountryInfo countryInfo;

    @SerializedName("deaths")
    private String deaths;

    @SerializedName("todayCases")
    private String todayCases;

    @SerializedName("todayDeaths")
    private String todayDeaths;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(String deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(String casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public CountryInfo getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(CountryInfo countryInfo) {
        this.countryInfo = countryInfo;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public int getCasesInt() {
        return Integer.parseInt(getCases());
    }

    public int getActiveInt() {
        return Integer.parseInt(getActive());
    }

    public int getRecoveredInt() {
        return Integer.parseInt(getRecovered());
    }

    public int getDeathsInt() {
        return Integer.parseInt(getDeaths());
    }

    public int getDeathInt() {
        return Integer.parseInt(getDeaths());
    }

    @Override
    public String toString() {
        return "Country [country = " + country + ", recovered = " + recovered + ", cases = " + cases + ", critical = " + critical + ", deathsPerOneMillion = " + deathsPerOneMillion + ", active = " + active + ", casesPerOneMillion = " + casesPerOneMillion + ", countryInfo = " + countryInfo + ",  deaths = " + deaths + ", todayCases = " + todayCases + ", todayDeaths = " + todayDeaths + "]";
    }

}
