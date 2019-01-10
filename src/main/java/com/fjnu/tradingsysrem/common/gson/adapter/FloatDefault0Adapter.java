package com.fjnu.tradingsysrem.common.gson.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;

public class FloatDefault0Adapter implements JsonSerializer<Float>, JsonDeserializer<Float> {
    @Override
    public Float deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            // 定义为int类型,如果后台返回""或者null,则返回0
            if (json.getAsString().equals("") || json.getAsString().equals("null")) {
                return 0.0f;
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsFloat();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Float src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}