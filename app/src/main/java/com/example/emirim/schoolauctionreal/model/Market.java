package com.example.emirim.schoolauctionreal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dodo1 on 2017-12-17.
 */

public class Market {
    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

    @SerializedName("type")
    public String type;

    @SerializedName("category")
    public String category;
}
