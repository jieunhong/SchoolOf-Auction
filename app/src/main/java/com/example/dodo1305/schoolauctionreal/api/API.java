package com.example.dodo1305.schoolauctionreal.api;

import com.example.dodo1305.schoolauctionreal.model.AccessToken;
import com.example.dodo1305.schoolauctionreal.model.Auth;
import com.example.dodo1305.schoolauctionreal.model.Dummy;
import com.example.dodo1305.schoolauctionreal.model.Market;
import com.example.dodo1305.schoolauctionreal.model.SignIn;
import com.example.dodo1305.schoolauctionreal.model.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by dodo1 on 2017-12-17.
 */

public interface API {
    @POST("/auth")
    Call<AccessToken> login(@Body Auth a);

    @POST("/users")
    Call<SignInResponse> signIn(@Body SignIn s);

    @POST("/market")
    Call postMarketContent(@Body Market m);

    @GET("/users/me/posts")
    Call getMyPosts();
}
