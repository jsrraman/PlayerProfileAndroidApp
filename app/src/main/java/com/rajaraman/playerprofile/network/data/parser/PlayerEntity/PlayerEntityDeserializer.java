// Created by rajaraman on Jan 01, 2015
package com.rajaraman.playerprofile.network.data.parser.PlayerEntity;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rajaraman.playerprofile.network.data.entities.BatFieldAvg;
import com.rajaraman.playerprofile.network.data.entities.BatFieldMatchStatistics;
import com.rajaraman.playerprofile.network.data.entities.BowlAvg;
import com.rajaraman.playerprofile.network.data.entities.PlayerEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class PlayerEntityDeserializer implements JsonDeserializer<ArrayList<PlayerEntity>>
{
    @Override
    public ArrayList<PlayerEntity> deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext jdc) throws JsonParseException
    {
        ArrayList<PlayerEntity> playerEntityList = new ArrayList<PlayerEntity>();

        // Get the passed Json element as JsonArray
        // The result is in the below form
        //result": [
        //{
        //    "countryId":1,
        //    "playerId":8917,
        //    ...
        //}]

        // Get the root json object
        JsonObject rootJsonObject = jsonElement.getAsJsonObject();

        // Get the result json array
        JsonArray resultJsonArray = rootJsonObject.get("result").getAsJsonArray();

        int countryId = 0;
        int playerId = 0;
        String playerUrl = "";
        String name = "";
        String country = "";
        String age = "";
        String battingStyle = "";
        String bowlingStyle = "";
        String thumbnailUrl = "";
        BatFieldAvg batFieldAvg = null;
        BowlAvg bowlAvg = null;

        for (int i = 0; i < resultJsonArray.size(); i++) {
            try {
                JsonElement rootJsonElement = resultJsonArray.get(i);

                final JsonObject jsonObject = rootJsonElement.getAsJsonObject();

                JsonElement jsonElementTemp = jsonObject.get("countryId");
                countryId = (jsonElementTemp != null) ? jsonElementTemp.getAsInt() : 0;

                jsonElementTemp = jsonObject.get("playerId");
                playerId = (jsonElementTemp != null) ? jsonElementTemp.getAsInt() : 0;

                jsonElementTemp = jsonObject.get("playerUrl");
                playerUrl = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : "";

                jsonElementTemp = jsonObject.get("name");
                name = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : "";

                jsonElementTemp = jsonObject.get("country");
                country = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : "";

                jsonElementTemp = jsonObject.get("age");
                age = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : "";

                jsonElementTemp = jsonObject.get("battingStyle");
                battingStyle = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : "";

                jsonElementTemp = jsonObject.get("bowlingStyle");
                bowlingStyle = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : "";

                jsonElementTemp = jsonObject.get("thumbnailUrl");
                thumbnailUrl = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : "";

                // Delegate the deserialization of "battingAndFieldingAvg" to the jdc with BatFieldAvg class
                jsonElementTemp = jsonObject.get("battingAndFieldingAvg");
                batFieldAvg = (jsonElementTemp != null) ?
                        (BatFieldAvg) jdc.deserialize(jsonElementTemp, BatFieldAvg.class) : null;

                // Delegate the deserialization of "battingAndFieldingAvg" to the jdc with BowlAvg class
                jsonElementTemp = jsonObject.get("bowlingAvg");
                bowlAvg = (jsonElementTemp != null) ?
                        (BowlAvg) jdc.deserialize(jsonElementTemp, BowlAvg.class) : null;
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                PlayerEntity playerEntity = new PlayerEntity(countryId, playerUrl, thumbnailUrl,
                        playerId, name, country, age, battingStyle,
                        bowlingStyle, batFieldAvg, bowlAvg);

                playerEntityList.add(playerEntity);
            }
        }

        return playerEntityList;
    }
}