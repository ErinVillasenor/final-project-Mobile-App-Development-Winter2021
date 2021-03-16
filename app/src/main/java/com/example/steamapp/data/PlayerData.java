package com.example.steamapp.data;

import com.google.gson.annotations.SerializedName;

public class PlayerData {
    @SerializedName("response")
    public PlayerSummary playerSummary;

    // should we have these here? maybe... I'm still unsure...
    // ownedGames
    // friends

    public PlayerData(){
        // this.playerSummary = null; CHANGE IT TO THIS ONE
        this.playerSummary = new PlayerSummary(); // JUST FOR TESTING PURPOSES, SINCE I DON'T HAVE THE Recycew
    }

    public PlayerSummary getPlayerSummary() { return playerSummary; }
}
