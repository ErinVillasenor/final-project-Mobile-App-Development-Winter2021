package com.example.steamapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FriendsList {
    @SerializedName("friendslist")
    public List<Friend> friends;

    public FriendsList(){
        this.friends = null;
    }

    public List<Friend> getFriends() { return friends; }
}