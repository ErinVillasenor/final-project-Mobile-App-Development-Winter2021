package com.example.steamapp.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SteamService {

    @GET("ISteamUser/GetPlayerSummaries/v0002")
    Call<PlayerData> searchPlayer(
            @Query("steamids") String player_id,
            @Query("key") String api_key
    );
}
