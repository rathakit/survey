package com.nimbl3.survey.apis;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * SurveyJsonParser - To deserialize the json string to be the array of Gson with Survey type.
 * @param <T>
 */
public class SurveyJsonParser<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonArray content = json.getAsJsonArray();
        return new Gson().fromJson(content, typeOfT);
    }
}
