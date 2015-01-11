// Created by rajaraman on Jan 01, 2015
package com.rajaraman.playerprofile.network.data.parser.PlayerEntity;

import com.google.gson.Gson;
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

public class BatFieldMatchStatisticsDeserializer implements JsonDeserializer<BatFieldMatchStatistics>
{
    @Override
    public BatFieldMatchStatistics deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext jdc) throws JsonParseException
    {
        // Get the passed Json element as Json object
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String defaultValue = "-";

        String matches = defaultValue;
        String runs = defaultValue;
        String highest = defaultValue;
        String average = defaultValue;
        String hundreds = defaultValue;
        String fifties = defaultValue;


        JsonElement jsonElementTemp = jsonObject.get("mat");
        matches = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("runs");
        runs = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("highest");
        highest = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("average");
        average = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("hundreds");
        hundreds = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        jsonElementTemp = jsonObject.get("fifties");
        fifties = (jsonElementTemp != null) ? jsonElementTemp.getAsString() : defaultValue;

        BatFieldMatchStatistics batFieldMatchStatistics = new BatFieldMatchStatistics();

        batFieldMatchStatistics.setMatches(matches);
        batFieldMatchStatistics.setRuns(runs);
        batFieldMatchStatistics.setHighest(highest);
        batFieldMatchStatistics.setAverage(average);
        batFieldMatchStatistics.setHundreds(hundreds);
        batFieldMatchStatistics.setFifties(fifties);

        return batFieldMatchStatistics;
    }
}