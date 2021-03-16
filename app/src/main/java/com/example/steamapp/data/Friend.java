package com.example.steamapp.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Friend {
    private String steamid;

    // default constructor
    public Friend() {
        this.steamid = null;
    }

    // non-default constructor
    public Friend(String steamid) {
        this.steamid = steamid;
    }

    // getters
    public String getSteamid() { return this.steamid; }

    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<Friend> {
        @Override
        public Friend deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject friendsListObj = json.getAsJsonObject();
            JsonArray friendsArr = friendsListObj.getAsJsonArray("friends");
            JsonObject friendsObj = friendsArr.get(0).getAsJsonObject();

            return new Friend(
                    friendsObj.getAsJsonPrimitive("steamid").getAsString()
            );
        }
    }
}
