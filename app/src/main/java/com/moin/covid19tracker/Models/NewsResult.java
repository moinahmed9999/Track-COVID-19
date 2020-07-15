package com.moin.covid19tracker.Models;

import com.google.gson.annotations.SerializedName;

public class NewsResult {
    @SerializedName("snippet")
    private String snippet;

    @SerializedName("image")
    private String image;

    @SerializedName("news_link")
    private String news_link;

    @SerializedName("title")
    private String title;

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNews_link() {
        return news_link;
    }

    public void setNews_link(String news_link) {
        this.news_link = news_link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
