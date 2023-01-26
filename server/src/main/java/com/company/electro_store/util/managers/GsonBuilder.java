package com.company.electro_store.util.managers;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;

public class GsonBuilder {
    private static final Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().registerTypeAdapter(Instant.class, new TypeAdapter<Instant>() {
            @Override
            public void write(JsonWriter jsonWriter, Instant instant) throws IOException {
                if (instant == null) {
                    jsonWriter.nullValue();
                } else {
                    jsonWriter.value(instant.toString());
                }
            }
            @Override
            public Instant read(JsonReader jsonReader) throws IOException {
                try {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    String instant = jsonReader.nextString();
                    if (instant == null) {
                        return null;
                    }
                    return ZonedDateTime.parse(instant).toInstant();
                } catch (IllegalArgumentException exception) {
                    throw new JsonParseException(exception);
                }
            }
        })
        .create();
    
    public static Gson getGson() {
        return gson;
    }
}
