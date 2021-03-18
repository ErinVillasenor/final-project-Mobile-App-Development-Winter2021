package com.example.steamapp.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;

public class GameInfo implements Serializable {
    private String name;

    @SerializedName("img_logo_url")
    private String imgLogoUrl;

    // default constructor
    public GameInfo() {
        this.name = null;
        this.imgLogoUrl = null;
    }

    // non-default constructor
    public GameInfo(String name, String imgLogoUrlHash) {
        this.name = name;
        this.imgLogoUrl = imgLogoUrlHash;  //api only provides the hash of the url

       /*
        Below is how to convert the imgLogoUrl hash into a valid jpg url.
        We will likely need to modify the constructor to include the steamID.

        String imgUrlBeg = "http://media.steampowered.com/steamcommunity/public/images/apps/";
        String imgUrlMid = steamID + "/" + imgLogoUrlHash;
        String imgUrlEnd = ".jpg";
        this.imgLogoUrl = imgUrlBeg + imgUrlMid + imgUrlEnd;
        */
    }

    // getters
    public String getName() { return this.name; }
    public String getImgLogoUrl() { return this.imgLogoUrl; }

    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<GameInfo> {

        @Override
        public GameInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject gamesObj = json.getAsJsonObject();
            
            return new GameInfo(
                    gamesObj.getAsJsonPrimitive("name").getAsString(),
                    gamesObj.getAsJsonPrimitive("img_logo_url").getAsString()
            );
        }
    }
}
