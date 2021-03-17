package com.example.steamapp.data;

import com.google.gson.annotations.SerializedName;

public class GamesList {
    @SerializedName("response")
    public Game games;

    public GamesList(){
        this.games = null;
    }

    public Game getGames() { return games; }
}