// Created by rajaraman on Jan 01, 2015
package com.rajaraman.playerprofile.network.data.parser.PlayerEntity;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rajaraman.playerprofile.network.data.entities.BatFieldAvg;
import com.rajaraman.playerprofile.network.data.entities.BatFieldMatchStatistics;
import com.rajaraman.playerprofile.network.data.entities.BowlAvg;
import com.rajaraman.playerprofile.network.data.entities.BowlMatchStatistics;

import java.lang.reflect.Type;

public class BowlAvgDeserializer implements JsonDeserializer<BowlAvg>
{
    @Override
    public BowlAvg deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext jdc) throws JsonParseException
    {
        // Get the passed Json element as Json object
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // Delegate the deserialization of "tests" to the jdc with BowlMatchStatistics class
        BowlMatchStatistics tests = jdc.deserialize(jsonObject.get("tests"),
                BowlMatchStatistics.class);

        // Delegate the deserialization of "odis" to the jdc with BowlMatchStatistics class
        BowlMatchStatistics odis = jdc.deserialize(jsonObject.get("odis"),
                BowlMatchStatistics.class);

        BowlAvg bowlAvg = new BowlAvg();

        bowlAvg.setTests(tests);
        bowlAvg.setOdis(odis);

        return bowlAvg;
    }
}