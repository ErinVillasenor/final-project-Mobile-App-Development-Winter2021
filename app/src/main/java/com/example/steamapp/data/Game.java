package com.example.steamapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Game {
    @SerializedName("games")
    public List<GameInfo> gameInfo;

    @SerializedName("game_count")
    public int gameCount;

    public Game(){
        this.gameInfo = null;
        this.gameCount = 0;
    }

    public List<GameInfo> getGameInfo() { return gameInfo; }

    public int getGameCount() { return gameCount; }
}
