package com.moin.covid19tracker.Models;

import com.google.gson.annotations.SerializedName;

public class CountryTimeline {
    @SerializedName("timeline")
    private WorldTimeline timeline;

    public WorldTimeline getTimeline() {
        return timeline;
    }

    public void setTimeline(WorldTimeline timeline) {
        this.timeline = timeline;
    }
}
