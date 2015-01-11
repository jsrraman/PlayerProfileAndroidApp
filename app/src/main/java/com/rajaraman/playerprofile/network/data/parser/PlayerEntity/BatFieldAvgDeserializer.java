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

public class BatFieldAvgDeserializer implements JsonDeserializer<BatFieldAvg>
{
    @Override
    public BatFieldAvg deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext jdc) throws JsonParseException
    {
        // Get the passed Json element as Json object
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // Delegate the deserialization of "tests" to the jdc with BatFieldMatchStatistics class
        BatFieldMatchStatistics tests = jdc.deserialize(jsonObject.get("tests"),
                                                                    BatFieldMatchStatistics.class);

        // Delegate the deserialization of "odis" to the jdc with BatFieldMatchStatistics class
        BatFieldMatchStatistics odis = jdc.deserialize(jsonObject.get("odis"),
                                                                    BatFieldMatchStatistics.class);

        BatFieldAvg batFieldAvg = new BatFieldAvg();

        batFieldAvg.setTests(tests);
        batFieldAvg.setOdis(odis);

        return batFieldAvg;
    }
}