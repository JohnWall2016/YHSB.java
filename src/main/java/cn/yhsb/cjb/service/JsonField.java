package cn.yhsb.cjb.service;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

public class JsonField {
    protected String value;

    public String getValue() {
        return value;
    }

    public static class Adapter implements JsonAdapter<JsonField> {

        @Override
        public JsonElement serialize(JsonField src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.value);
        }

        @Override
        public JsonField deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            var field = new JsonField();
            field.value = json.getAsString();
            return field;
        }

    }
}