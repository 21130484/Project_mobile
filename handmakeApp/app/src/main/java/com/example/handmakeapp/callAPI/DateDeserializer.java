package com.example.handmakeapp.callAPI;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer extends TypeAdapter<Date> {
    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if(value != null) out.value(value.getTime());
        else out.nullValue();
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        long timestamp = in.nextLong();
        return new Date(timestamp);
    }


//
//    @Override
//    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        return new Date(json.getAsJsonPrimitive().getAsLong());
//    }
}
