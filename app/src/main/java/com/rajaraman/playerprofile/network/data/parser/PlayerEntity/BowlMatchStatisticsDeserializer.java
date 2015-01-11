// Created by rajaraman on Jan 01, 2015
package com.rajaraman.playerprofile.network.data.parser.PlayerEntity;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rajaraman.playerprofile.network.data.entities.BatFieldMatchStatistics;
import com.rajaraman.playerprofile.network.data.entities.BowlMatchStatistics;

import java.lang.reflect.Type;

public class BowlMatchStatisticsDeserializer implements JsonDeserializer<BowlMatchStatistics>
{
    @Override
    public BowlMatchStatistics deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext jdc) throws JsonParseException
    {
        // Get the passed Json element as Json object
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String defaultValue = "-";

        String matches = defaultValue;
        String wkts = defaultValue;
        String bestMatchBowling = defaultValue;
        String average = defaultValue;
        String fourWktsInInns = defaultValue;
        String fiveWktsInInns = defaultValue;
        String tenWktsInMatch = defaultValue;

        JsonElement jsonElementTemp = jsonObject.get("mat");
        matches = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("wkts");
        wkts = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("bestMatchBowling");
        bestMatchBowling = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("average");
        average = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("fourWktsInInns");
        fourWktsInInns = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("fiveWktsInInns");
        fiveWktsInInns = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("tenWktsInMatch");
        tenWktsInMatch = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        final BowlMatchStatistics bowlMatchStatistics = new BowlMatchStatistics();

        bowlMatchStatistics.setMatches(matches);
        bowlMatchStatistics.setWickets(wkts);
        bowlMatchStatistics.setBestMatchBowling(bestMatchBowling);
        bowlMatchStatistics.setAverage(average);
        bowlMatchStatistics.setFourWktsInInns(fourWktsInInns);
        bowlMatchStatistics.setFiveWktsInInns(fiveWktsInInns);
        bowlMatchStatistics.setTenWktsInMatch(tenWktsInMatch);

        return bowlMatchStatistics;
    }
}