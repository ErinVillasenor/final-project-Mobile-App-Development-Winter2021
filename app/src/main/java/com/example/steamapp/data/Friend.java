package com.example.steamapp.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;

public class Friend {
    @SerializedName("friends")
    public List<FriendInfo> friendInfo;

    public Friend(){
        this.friendInfo = null;
    }

    public List<FriendInfo> getFriendInfo() { return friendInfo; }
}
