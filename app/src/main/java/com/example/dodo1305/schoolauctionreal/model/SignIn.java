package com.example.dodo1305.schoolauctionreal.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dodo1 on 2017-12-17.
 */

public class SignIn {
    @SerializedName("id")
    public String id;

    @SerializedName("password")
    public String password;

    @SerializedName("name")
    public String name;

    @SerializedName("school")
    public String school;

    @SerializedName("phone")
    public String phone;
}
