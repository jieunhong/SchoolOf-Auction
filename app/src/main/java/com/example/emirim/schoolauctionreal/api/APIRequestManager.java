package com.example.emirim.schoolauctionreal.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dodo1 on 2017-12-17.
 */

public class APIRequestManager {
    public static String SERVER_URL = "https://school-auction.herokuapp.com";

    private static APIRequestManager apiManager;
    private API api;
    private Retrofit retrofit;

    public static APIRequestManager getInstance() {
        apiManager = new APIRequestManager();

        return apiManager;
    }

    public API prepareAPIRequest() {
        retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(API.class);
    }
}
