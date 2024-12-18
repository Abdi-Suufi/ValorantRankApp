package com.example.valorantrankapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ValorantApi {
    @POST("/get-rank")
    Call<RankData> getRank(@Body UsernameTag usernameTag);
}
